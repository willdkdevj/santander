package br.com.infotera.santander.financiamento.service;

import br.com.infotera.santander.financiamento.rqrs.SimulacaoRQ;
import br.com.infotera.santander.financiamento.rqrs.SimulacaoRS;
import br.com.infotera.common.ErrorException;
import br.com.infotera.common.WSCliente;
import br.com.infotera.common.WSIntegrador;
import br.com.infotera.common.WSReserva;
import br.com.infotera.common.enumerator.WSIntegracaoStatusEnum;
import br.com.infotera.common.enumerator.WSMensagemErroEnum;
import br.com.infotera.common.pagto.WSPagtoBoleto;
import br.com.infotera.common.pagto.WSPagtoCartaoDebito;
import br.com.infotera.common.pagto.WSPagtoForma;
import br.com.infotera.common.pagto.WSPagtoMeioFinanciamento;
import br.com.infotera.common.pagto.WSPagtoMeioParcela;
import br.com.infotera.common.pagto.WSPagtoValor;
import br.com.infotera.common.pagto.financiamento.WSTermoFinanciamento;
import br.com.infotera.common.pagto.financiamento.rqrs.WSPagtoAnaliseRQ;
import br.com.infotera.common.pagto.financiamento.rqrs.WSPagtoAnaliseRS;
import br.com.infotera.common.util.Utils;
import br.com.infotera.santander.financiamento.client.SantanderClient;
import br.com.infotera.santander.financiamento.model.Lgpd;
import br.com.infotera.santander.financiamento.model.ListTerms;
import br.com.infotera.santander.financiamento.model.PartnerClient;
import br.com.infotera.santander.financiamento.model.Simulation;
import br.com.infotera.santander.financiamento.util.UtilsWS;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SimularWS {

    @Autowired
    private SantanderClient santanderClient;

    public WSPagtoAnaliseRS simularFinanciamento(WSPagtoAnaliseRQ pagtoAnaliseRQ) throws ErrorException {
        
        SimulacaoRQ simulationRQ = montarRequestSimulacao(pagtoAnaliseRQ);
        
        SimulacaoRS simulationRtn = santanderClient.criarSimulacao(pagtoAnaliseRQ.getIntegrador(), simulationRQ);
        
        WSPagtoMeioFinanciamento pagtoMeioFinanciamento = montarPagtoMeioFinanciamento(pagtoAnaliseRQ.getIntegrador(), simulationRtn);

        return new WSPagtoAnaliseRS(pagtoAnaliseRQ.getIntegrador(), pagtoMeioFinanciamento, pagtoAnaliseRQ.getFinanciamentoCadastro().getCliente(), WSIntegracaoStatusEnum.OK);
    }

    private WSPagtoMeioFinanciamento montarPagtoMeioFinanciamento(WSIntegrador integrador, SimulacaoRS simulationRS) throws ErrorException {
        WSPagtoMeioFinanciamento pagtoMeioFinanciamento = null;
        
        List<WSPagtoMeioParcela> financiamentoParcelasList = null;
        List<WSPagtoForma> formaPagtoList = null;
        
        if(simulationRS != null) {
            try {
                try {
                    if(simulationRS.getInstallments() != null && !Utils.isListNothing(simulationRS.getInstallments())){
                        List<WSPagtoMeioParcela> parcelasList = new ArrayList<>();
                        simulationRS.getInstallments().stream()
                            .map(installment -> {
                                WSPagtoMeioParcela parcela = new WSPagtoMeioParcela();
                                parcela.setNrParcela(installment.getNumber());
                                
                                WSPagtoValor pagtoValor = new WSPagtoValor();
                                pagtoValor.setVlPagto(installment.getValue());
                                
                                parcela.setPagtoValor(pagtoValor);

                                return parcela;
                            }).forEachOrdered((parcela) -> {
                                parcelasList.add(parcela);
                            });
                        if(!Utils.isListNothing(parcelasList)){
                            financiamentoParcelasList = new ArrayList<>(parcelasList);
                        }
                    }
                    
                } catch (Exception ex) {
                    throw new ErrorException(integrador, SimularWS.class, "montaPagtoFinanciamento", 
                            WSMensagemErroEnum.ADI, "Erro ao obter os números e valores das parcelas.", WSIntegracaoStatusEnum.INCONSISTENTE, ex, false);
                }
                    
                try {
                    if(simulationRS.getLiquidationTypeCode() != null){
                        formaPagtoList = new ArrayList<>();
                        WSPagtoForma formaPagto = null;
                        switch(simulationRS.getLiquidationTypeCode()){
                            case "B":
                                WSPagtoBoleto boleto = new WSPagtoBoleto();
                                boleto.setCdBoleto(simulationRS.getLiquidationTypeCode());
                                boleto.setCdEspecie("BOLETO");
                                formaPagto = (WSPagtoForma) boleto;
                                break;
                                
                            case "D":
                                WSPagtoCartaoDebito debito = new WSPagtoCartaoDebito();
                                debito.setIdExterno(simulationRS.getLiquidationTypeCode());
                                debito.setDsPagamento("DEBITO");
                                formaPagto = (WSPagtoForma) debito;
                                break;
                                
                            default:
                                throw new ErrorException(integrador, SimularWS.class, "montaPagtoFinanciamento", 
                                    WSMensagemErroEnum.ADI, "Não foi retornado um código válido para o pagamento da proposta.", WSIntegracaoStatusEnum.INCONSISTENTE, null, false);
                        }
                        formaPagtoList.add(formaPagto);
                    }
                } catch (ErrorException ex) {
                    throw new ErrorException(integrador, SimularWS.class, "montaPagtoFinanciamento", 
                            WSMensagemErroEnum.ADI, "Erro ao montar a Lista de forma de pagamento (WSPagtoForma)", WSIntegracaoStatusEnum.INCONSISTENTE, ex, false);
                }
                
                pagtoMeioFinanciamento = new WSPagtoMeioFinanciamento();
                
                if(financiamentoParcelasList != null && !Utils.isListNothing(financiamentoParcelasList)){
                    pagtoMeioFinanciamento.setPagtoMeioParcelaList(financiamentoParcelasList);
                }
                
                if(formaPagtoList != null && !Utils.isListNothing(formaPagtoList)){
                    pagtoMeioFinanciamento.setFormasPagtoList(formaPagtoList);
                }
                
                try {    
                    if(simulationRS.getIsValidFirstInstallmentDueDate()){
                        pagtoMeioFinanciamento.setDtPrimeiroVencimento(Utils.toDate(simulationRS.getFirstInstallmentDueDate(), "yyyy-MM-dd"));
                    }
                    pagtoMeioFinanciamento.setDtMinEntrada(Utils.toDate(simulationRS.getMinAllowedFirstInstallmentDueDate(), "yyyy-MM-dd"));
                    pagtoMeioFinanciamento.setDtMaxEntrada(Utils.toDate(simulationRS.getMaxAllowedFirstInstallmentDueDate(), "yyyy-MM-dd"));
                } catch (Exception ex) {
                    throw new ErrorException(integrador, SimularWS.class, "montaPagtoFinanciamento", 
                            WSMensagemErroEnum.ADI, "Erro ao obter as datas para parcelamento.", WSIntegracaoStatusEnum.INCONSISTENTE, ex, false);
                }
                
                try {
                    pagtoMeioFinanciamento.setVlEntrada(simulationRS.getEntryValue());
                    pagtoMeioFinanciamento.setVlFinanciado(simulationRS.getFinancedValue());
                    pagtoMeioFinanciamento.setVlTotalCompra(simulationRS.getTotalAmountFinanced());
                    pagtoMeioFinanciamento.setVlMinEntrada(simulationRS.getMinFinancedValue());
                    pagtoMeioFinanciamento.setVlMaxEntrada(simulationRS.getMaxFinancedValue());
                } catch (Exception ex) {
                    throw new ErrorException(integrador, SimularWS.class, "montaPagtoFinanciamento", 
                            WSMensagemErroEnum.ADI, "Erro ao obter os valores do financiamento.", WSIntegracaoStatusEnum.INCONSISTENTE, ex, false);
                }
                
                try {
                    pagtoMeioFinanciamento.setQtdParcelas(simulationRS.getInstallmentAmount());
                    pagtoMeioFinanciamento.setPcJuros(simulationRS.getAnnualCalcContractFee());
                    pagtoMeioFinanciamento.setPcCetMes(simulationRS.getMonthlyInternalReturnRate());
                    pagtoMeioFinanciamento.setPcCetAno(simulationRS.getAnnualInternalReturnRate());
                } catch (Exception ex) {
                    throw new ErrorException(integrador, SimularWS.class, "montaPagtoFinanciamento", 
                            WSMensagemErroEnum.ADI, "Erro ao obter os valores do financiamento.", WSIntegracaoStatusEnum.INCONSISTENTE, ex, false);
                }
                
                try {
                    pagtoMeioFinanciamento.setCdLocalizador(simulationRS.getProposalId());
                    pagtoMeioFinanciamento.setCdAutorizacao(simulationRS.getProposalId() + "#"
                                                        + simulationRS.getSaleOffTypeCode() + "#"
                                                        + simulationRS.getPaymentPlan() + "#"
                                                        + simulationRS.getRegistrationFeeType());
                } catch (Exception ex) {
                    throw new ErrorException(integrador, SimularWS.class, "montaPagtoFinanciamento", 
                            WSMensagemErroEnum.ADI, "Erro ao obter os dados para localização da proposta.", WSIntegracaoStatusEnum.INCONSISTENTE, ex, false);
                }

            } catch (ErrorException ee) {
                throw ee;
            } catch (Exception ex) {
                throw new ErrorException(integrador, SimularWS.class, "montaPagtoFinanciamento", 
                        WSMensagemErroEnum.ADI, "Erro ao obter os dados para o PagtoFinanciamento.", WSIntegracaoStatusEnum.ATENCAO, ex, false);
            }
        }

        return pagtoMeioFinanciamento;
    }

    private SimulacaoRQ montarRequestSimulacao(WSPagtoAnaliseRQ pagtoAnaliseRQ) throws ErrorException {
        SimulacaoRQ simulacao = null;
        try {
            Simulation simulation = montarSimulation(pagtoAnaliseRQ.getIntegrador(), pagtoAnaliseRQ.getPagtoMeioFinanciamento(), pagtoAnaliseRQ.getReserva());
                        
            PartnerClient partnerClient = montarDadosCliente(pagtoAnaliseRQ.getIntegrador(), pagtoAnaliseRQ.getFinanciamentoCadastro().getCliente());
            
            simulacao = montarTermosContratuais(pagtoAnaliseRQ.getIntegrador(), simulation, partnerClient, pagtoAnaliseRQ.getPagtoMeioFinanciamento().getTermoFinanciamentoList());
            
        } catch (ErrorException ex){
            throw new ErrorException(pagtoAnaliseRQ.getIntegrador(), SimularWS.class, "montarSimulation", 
                    WSMensagemErroEnum.PSE, "Erro ao obter as Formas de Pagamento do Fornecedor.", WSIntegracaoStatusEnum.ATENCAO, ex, false);
        }
        
        return new SimulacaoRQ();
    }
    
    private Simulation montarSimulation(WSIntegrador integrador, WSPagtoMeioFinanciamento pagtoMeioFinanciamento, WSReserva reserva) throws ErrorException{
        Simulation simulation = null;
        String[] codesIntegra = null;
        String codigoLoja = null;
        
        try {
            codesIntegra = integrador.getSessao() != null ? integrador.getSessao().getCdChave().split("#") : null;
            if(codesIntegra != null){
                codigoLoja = codesIntegra[1];
            } else {
                throw new ErrorException(integrador, SimularWS.class, "montarSimulation", 
                        WSMensagemErroEnum.PSE, "Erro ao obter as Formas de Pagamento do Fornecedor.", WSIntegracaoStatusEnum.ATENCAO, null, false);
            }
            
            String cdProduto = pagtoMeioFinanciamento.getProdutoFinanciamentoList().stream()
                    .filter(produto -> produto.getCdProduto() != null)
                    .findFirst()
                    .orElseThrow(ErrorException::new)
                    .getCdProduto();
                    
            simulation = new Simulation();
            simulation.setProductCode(cdProduto);
            simulation.setSupplierChannelCode(codigoLoja);
            simulation.setFinancedValue(pagtoMeioFinanciamento.getVlFinanciado());
            simulation.setEntryValue(pagtoMeioFinanciamento.getVlEntrada());
            simulation.setTravelDate(Utils.formatData(reserva.getDtUtilizacao(), "yyyy-MM-dd"));
            
            // Verifica se existe cupom de desconto
            String cupom = pagtoMeioFinanciamento.getCdAutorizacao() != null ? pagtoMeioFinanciamento.getCdAutorizacao() : null;
            simulation.setCupom(cupom);
            
        } catch (ErrorException ee){
            throw new ErrorException(integrador, SimularWS.class, "montarSimulation", 
                    WSMensagemErroEnum.PSE, "Erro ao obter as Formas de Pagamento do Fornecedor.", WSIntegracaoStatusEnum.ATENCAO, ee, false);
        }
        
        return simulation;
    }
    
    private PartnerClient montarDadosCliente(WSIntegrador integrador, WSCliente cliente) throws ErrorException{
        PartnerClient partnerClient = null;
        try {
            if(UtilsWS.verificarCliente(integrador, cliente)){
                partnerClient = new PartnerClient();
                partnerClient.setDocumentType(cliente.getDocumento().getDocumentoTipo().name());
                partnerClient.setDocumentNumber(cliente.getDocumento().getNrDocumento());
                partnerClient.setBirthdayDate(Utils.formatData(cliente.getDtNascimento(), "yyyy-MM-dd"));
                partnerClient.setMainTelephoneAreaCode(cliente.getTelefone().getNrDDD());
                partnerClient.setMainTelephoneNumber(cliente.getTelefone().getNrTelefone());
            
            }
        } catch (ErrorException ee){
            throw new ErrorException(integrador, SimularWS.class, "montarDadosCliente", 
                            WSMensagemErroEnum.PSE, "Erro ao obter as Formas de Pagamento do Fornecedor.", WSIntegracaoStatusEnum.ATENCAO, ee, false);
        }
        
        return partnerClient;
    }

    private SimulacaoRQ montarTermosContratuais(WSIntegrador integrador, Simulation simulation, PartnerClient partnerClient, List<WSTermoFinanciamento> termoFinanciamentoList) throws ErrorException {
        SimulacaoRQ simulacao = null;
        
        try {
            WSTermoFinanciamento termo = termoFinanciamentoList.stream()
                    .filter(termoFin -> termoFin.getCdTermo().equals("127")) // Caso seja relacionado ao CSC
                    .findFirst()
                    .orElseThrow(RuntimeException::new);
                    
            if(termo.getCdTermo().equals("127")){ 
                ListTerms listTerms = new ListTerms();
                listTerms.setAnswerCompanyOffer(termo.isRespOfertaCompanhia() ? "S" : "N");

                Lgpd lgpd = new Lgpd();
                lgpd.setAnswerGroupOffer(termo.isRespOfertaGrupo() ? "S" : "N");
                lgpd.setAnswerPartnerOffer(termo.isRespOfertaParceiro() ? "S" : "N");

                simulacao = new SimulacaoRQ();
                simulacao.setListTerms(listTerms);
                simulacao.setLgpd(lgpd);
            }
            
            simulacao.setSimulation(simulation);
            simulacao.setPartnerClient(partnerClient);
            
        }catch (Exception ee){
            throw new ErrorException(integrador, SimularWS.class, "montarDadosCliente", 
                            WSMensagemErroEnum.PSE, "Erro ao obter as Formas de Pagamento do Fornecedor.", WSIntegracaoStatusEnum.ATENCAO, ee, false);
        }
        
        return simulacao;
    }
}
