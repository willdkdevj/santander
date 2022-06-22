package br.com.infotera.santander.financiamento.service;

import br.com.infotera.santander.financiamento.rqrs.SimulacaoRQ;
import br.com.infotera.santander.financiamento.rqrs.IntegrationCodeRS;
import br.com.infotera.santander.financiamento.rqrs.TermosCondicoesRS;
import br.com.infotera.santander.financiamento.rqrs.SimulacaoRS;
import br.com.infotera.common.ErrorException;
import br.com.infotera.common.WSCliente;
import br.com.infotera.common.WSIntegrador;
import br.com.infotera.common.WSReserva;
import br.com.infotera.common.enumerator.WSIntegracaoStatusEnum;
import br.com.infotera.common.enumerator.WSMensagemErroEnum;
import br.com.infotera.common.pagto.WSPagtoMeioFinanciamento;
import br.com.infotera.common.pagto.WSPagtoMeioParcela;
import br.com.infotera.common.pagto.WSPagtoValor;
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
    private SessaoWS sessaoWS;

    @Autowired
    private SantanderClient santanderClient;

    public WSPagtoAnaliseRS simularFinanciamento(WSPagtoAnaliseRQ pagtoAnaliseRQ) throws ErrorException {
        WSIntegrador integrador = null;
        if (pagtoAnaliseRQ.getIntegrador().getSessao() == null) {
            integrador = sessaoWS.abreSessao(pagtoAnaliseRQ.getIntegrador());
        } else {
            integrador = pagtoAnaliseRQ.getIntegrador();
        }
        
        // Chamada para obter o Código da Loja (ID Loja) - Deve ser realizada nova chamada para obter novo código, caso seja feita nova análise
        IntegrationCodeRS integrationCode = santanderClient.getProductCSC(integrador);
        
        // Retorna os termos e condições para uso do produto de financiamento (Retorno de Contrato no formato HTML)
        TermosCondicoesRS termosCondGeral = santanderClient.retornarTermosCondicoes(integrador); // VERIFICAR ONDE DEVERA SER PASSADA O TERMO (HTML)
        
        // Retorna as questões a serem apresentadas ao cliente e seu aceite deve ser avaliada para realizar a simulação
        TermosCondicoesRS termosLGPD = santanderClient.retornarTermosLGPD(integrador); // VERIFICAR ONDE DEVERA SER PASSADA AS QUESTÕES SOBRE O TERMO (TEXTO)
        
        SimulacaoRQ simulationRQ = montarRequestSimulacao(pagtoAnaliseRQ, integrationCode, termosLGPD);
        SimulacaoRS simulationRtn = santanderClient.criarSimulacao(integrador, simulationRQ);
        
        WSPagtoMeioFinanciamento pagtoMeioFinanciamento = montarPagtoMeioFinanciamento(integrador, integrationCode, simulationRtn);

        return new WSPagtoAnaliseRS(integrador, pagtoMeioFinanciamento, pagtoAnaliseRQ.getFinanciamentoCadastro().getCliente(), WSIntegracaoStatusEnum.OK);
    }

    private WSPagtoMeioFinanciamento montarPagtoMeioFinanciamento(WSIntegrador integrador, IntegrationCodeRS integrationCode, SimulacaoRS simulationRS) throws ErrorException {
        WSPagtoMeioFinanciamento pagtoMeioFinanciamento = null;
        
        List<WSPagtoMeioParcela> financiamentoParcelasList = null;
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

//                    List<WSPagtoForma> formaPagtoList = new ArrayList<>();
//                    preAnaliseRS.getAtxResponse().getPaymentForms().stream()
//                            .map(p -> {
//                                WSPagtoForma formaPagto = null;
//                                if(p.getIntegrationCode().equals("CA")){
//                                    WSPagtoBoleto boleto = new WSPagtoBoleto();
//                                    boleto.setCdBoleto(String.valueOf(p.getId()));
//                                    boleto.setCdEspecie(p.getDescription());
//                                    formaPagto = (WSPagtoForma) boleto;
//                                } else if(p.getIntegrationCode().equals("DC")){
//                                    WSPagtoCartaoDebito debito = new WSPagtoCartaoDebito();
//                                    debito.setIdExterno(String.valueOf(p.getId()));
//                                    debito.setDsPagamento(p.getDescription());
//                                    formaPagto = (WSPagtoForma) debito;
//                                }
//                                return formaPagto;
//                            }).forEachOrdered( forma -> {
//                                formaPagtoList.add(forma);
//                            }); 
//                    }

                pagtoMeioFinanciamento = new WSPagtoMeioFinanciamento();
                
                if(financiamentoParcelasList != null && !Utils.isListNothing(financiamentoParcelasList)){
                    pagtoMeioFinanciamento.setPagtoMeioParcelaList(financiamentoParcelasList);
                }
                
                try {    
                    if(simulationRS.getIsValidFirstInstallmentDueDate() && simulationRS.getFirstInstallmentDueDate() != null){
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

//    private PreAnaliseRS chamarPreAnalise(WSIntegrador integrador, Customer customer, Purchase purchase, IntegrationCodeRS integrationCode) throws ErrorException {
//        PreAnaliseRQ preAnaliseRQ = null;
//        
//        if(customer != null && purchase != null && integrationCode != null){
//            preAnaliseRQ.setCustomer(customer);
//            preAnaliseRQ.setPurchase(purchase);
//            preAnaliseRQ.setUuid(integrationCode.getUuid().toString());
//        }
//
//        return santanderClient.preAnalise(integrador, preAnaliseRQ);
//    }
//
//    private SimulacaoRS chamarSimulacao(WSIntegrador integrador, IntegrationCodeRS integrationCode, Customer customer, PreAnaliseRS preAnaliseRS, WSPagtoAnaliseRQ pagtoAnaliseRQ) throws ErrorException {
//        
//        
//        SimulacaoRQ simulacaoRQ = null;
//        
//        try {
//            Customer customerSimulation = new Customer();
//            customerSimulation.setDocument(customer.getDocument());
//
//            Store storeSimulation = new Store();
//            storeSimulation.setTabId(integrationCode.getTabId());
//
//            try {
//                boolean tabfee = preAnaliseRS.getAtxResponse().getFees() != null; // Indicador de Pagamento TAB
//                if(tabfee) {
//                    Fee feeSimulation = new Fee();
//                    feeSimulation.setTabFee(tabfee);
//                    if(preAnaliseRS.getAtxResponse().getFees() != null && !preAnaliseRS.getAtxResponse().getFees().getTcExemption()) {
//                        feeSimulation.setTcFee(false);
//                    }
//                    feeSimulation.setTcExemption(preAnaliseRS.getAtxResponse().getFees().getTcExemption());
//                    simulacaoRQ.setFees(feeSimulation);
//                }
//
//            } catch (Exception ex) {
//                throw new ErrorException(integrador, SimularWS.class, "chamarSimulacao",
//                        WSMensagemErroEnum.ADI, "Erro ao obter os parâmetros de Indicador de Pagamento TAB", WSIntegracaoStatusEnum.ATENCAO, ex);
//            }
//
//            verificaFormaPagtoCliente(integrador, preAnaliseRS);
//            try {
//                Integer tpPagto = 0;
//                if (preAnaliseRS.getAtxResponse().getAccountHolder()) {
//                    if (pagtoAnaliseRQ.getPagtoMeioFinanciamento().getPagtoFormaEntrada().isStCartaoDebito()) {
//                        tpPagto = 8; // cartao de debito
//                    } else {
//                        tpPagto = 7; // outros meios de entrada(boleto,deposito etc)
//                    }
//                }
//
//                Payment payment = new Payment();
////                payment.setPaymentFormId(preAnaliseRS.getAtxResponse().); PAGAMENTO SELECIONADO
//            } catch (Exception ex) {
//                throw new ErrorException(integrador, SimularWS.class, "chamarSimulacao",
//                        WSMensagemErroEnum.ADI, "Erro ao obter os parâmetros para Identificação de Forma de Pagamento", WSIntegracaoStatusEnum.ATENCAO, ex);
//            }
//
//            simulacaoRQ.setTableNumber(preAnaliseRS.getAtxResponse().getPriceTable());
//            simulacaoRQ.setUuid(preAnaliseRS.getUuid());
//            simulacaoRQ.setCustomer(customerSimulation);
//            //            simulacaoRQ.setPayment();
//            simulacaoRQ.setStore(storeSimulation);
//        } catch (Exception ex) {
//            throw new ErrorException(integrador, SimularWS.class, "chamarSimulacao",
//                    WSMensagemErroEnum.ADI, "Erro ao obter os parâmetros para realizar a Simulação.", WSIntegracaoStatusEnum.ATENCAO, ex);
//        }
//
//        return santanderClient.simulacao(integrador, simulacaoRQ);
//    }
//
//    private Purchase montaPurchase(WSIntegrador integrador, IntegrationCodeRS integrationCode, WSPagtoMeioFinanciamento pagtoMeioFinanciamento) throws ErrorException {
//        Purchase purchase = null;
//        
//        // Retorna o objeto financiado referente a TAB (Cod Loja)
//        FinancedObjectRS financedObject = santanderClient.retornarFinancedObject(integrador, integrationCode.getTabId());
//        
//        // Retorna o SubSegmento na qual o Santander classifica o negócio (Agência)
//        SubSegmentRS subSegment = santanderClient.retornarSubSegment(integrador, integrationCode.getTabId());
//        
//        try {
//            purchase.setFinancedObject(financedObject.getId()); // ID Financed Object
//            
//            if(pagtoMeioFinanciamento.getVlEntrada() != null) {
//                purchase.setEntryValue(pagtoMeioFinanciamento.getVlEntrada()); // Valor de Entrada
//            }
//            
//            if(pagtoMeioFinanciamento.getVlParcela() != null) {
//                purchase.setInstallmentValue(pagtoMeioFinanciamento.getVlParcela()); // Valor das Parcelas
//            }
//            
//            if(pagtoMeioFinanciamento.getQtdParcelas() != null) {
//                purchase.setInstallmentQuantity(pagtoMeioFinanciamento.getQtdParcelas()); // N° Parcelas
//            }
//            
//            purchase.setSubsegment(Integer.parseInt(subSegment.getCode()));
//            purchase.setValue(pagtoMeioFinanciamento.getVlTotalCompra());
//            
//        } catch (Exception ex) {
//            throw new ErrorException(integrador, SimularWS.class, 
//                    "montaPurchase", WSMensagemErroEnum.ADI, "Erro ao obter os valores para realizar a Pré-Analise.", WSIntegracaoStatusEnum.ATENCAO, ex);
//        }
//
//        return purchase;
//    }
//
//    private Customer montaCustomer(WSIntegrador integrador, WSCliente cliente) throws ErrorException {
//        WSCliente clienteRetorno = null;
//        if(UtilsWS.verificarCliente(integrador, cliente)){
//            clienteRetorno = cliente;
//        }
//
//        return new Customer(Utils.formatData(clienteRetorno.getDtNascimento(), "yyyy-MM-dd'T'HH:mm:ssz"),
//                            clienteRetorno.getDocumento().getNrDocumento(),
//                            new CellPhone(clienteRetorno.getTelefone().getNrTelefone().substring(0, 1), 
//                                          clienteRetorno.getTelefone().getNrTelefone().substring(2, clienteRetorno.getTelefone().getNrTelefone().length() -1)));
//    }
//
//    private List<WSPagtoForma> retornaFormasPagto(WSIntegrador integrador) throws ErrorException {
//        List<ProductCodeRS> productCodeRSList = santanderClient.retornarProductCode(integrador);
//        
//        List<WSPagtoForma> pagtoFormaList = new ArrayList<>();
//        productCodeRSList.stream()
//                .map(pc -> {
//                    WSPagtoForma formaPagto = null;
//                    if(pc.getDescription().contains("BOLETO")){
//                        WSPagtoBoleto boleto = new WSPagtoBoleto();
//                        boleto.setIdExterno(String.valueOf(pc.getId()));
//                        boleto.setDsPagamento(pc.getDescription());
//                        formaPagto = (WSPagtoForma) boleto;
//                    } else {
//                        WSPagtoCartaoDebito debito = new WSPagtoCartaoDebito();
//                        debito.setIdExterno(String.valueOf(pc.getId()));
//                        debito.setDsPagamento(pc.getDescription());
//                        formaPagto = (WSPagtoForma) debito;
//                    }
//                    return formaPagto;
//                }).forEachOrdered( forma -> {
//                    pagtoFormaList.add(forma);
//                });
//           
//        return pagtoFormaList != null && !Utils.isListNothing(pagtoFormaList) ? pagtoFormaList : null;
//    }
//    
//    private WSPagtoForma verificaFormaPagtoCliente(WSIntegrador integrador, PreAnaliseRS preAnaliseRS) throws ErrorException{
//        WSPagtoForma pagtoForma = null;
//        try {
//            PaymentForm payment = null;
//            boolean correntista = preAnaliseRS.getAtxResponse().getAccountHolder();
//            
//            if(correntista){
//                payment = preAnaliseRS.getAtxResponse().getPaymentForms().stream()
//                        .filter(fp -> fp.getIntegrationCode().equals("DC"))
//                        .findFirst()
//                        .orElse(null);
//                if(payment != null){
//                    pagtoForma.setDsPagamento(payment.getDescription());
//                    pagtoForma.setPagtoFormaEntrada(new WSPagtoCartaoDebito());
//                }
//            } else {
//                payment = preAnaliseRS.getAtxResponse().getPaymentForms().stream()
//                        .filter(fp -> fp.getIntegrationCode().equals("CA"))
//                        .findFirst()
//                        .orElse(null);
//                if(payment != null){
//                    pagtoForma.setDsPagamento(payment.getDescription());
//                    pagtoForma.setPagtoFormaEntrada(new WSPagtoBoleto());
//                }
//            }
//            if(payment == null){
//                throw new ErrorException(integrador, SimularWS.class, 
//                    "verificaFormaPagtoCliente", WSMensagemErroEnum.ADI, "Erro ao obter um valor válido de Tipo de Pagamento.", WSIntegracaoStatusEnum.ATENCAO, null, false);
//            }
//        } catch(Exception ex) {
//            throw new ErrorException(integrador, SimularWS.class, 
//                    "verificaFormaPagtoCliente", WSMensagemErroEnum.ADI, "Erro ao obter as Formas de Pagamento do Fornecedor.", WSIntegracaoStatusEnum.ATENCAO, ex, false);
//        }
//        
//        return pagtoForma;
//    }

    private SimulacaoRQ montarRequestSimulacao(WSPagtoAnaliseRQ pagtoAnaliseRQ, IntegrationCodeRS integrationCode, TermosCondicoesRS termosLGPD) throws ErrorException {
        Simulation simulation = null;
        PartnerClient partnerClient = null;
        ListTerms listTerms = null;
        Lgpd lgpd = null;
        
        try {
            simulation = montarSimulation(pagtoAnaliseRQ.getIntegrador(), integrationCode, pagtoAnaliseRQ.getPagtoMeioFinanciamento(), pagtoAnaliseRQ.getReserva());
            
            
            partnerClient = montarDadosCliente(pagtoAnaliseRQ.getIntegrador(), pagtoAnaliseRQ.getFinanciamentoCadastro().getCliente());
            
            listTerms = new ListTerms();
            listTerms.setAnswerCompanyOffer(termosLGPD.getAnswerCompanyOffer());
            
            lgpd = new Lgpd();
            lgpd.setAnswerGroupOffer(termosLGPD.getAnswerGroupOffer());
            lgpd.setAnswerPartnerOffer(termosLGPD.getAnswerPartnerOffer());
            
        } catch (ErrorException ex){
            throw new ErrorException(pagtoAnaliseRQ.getIntegrador(), SimularWS.class, "montarSimulation", 
                    WSMensagemErroEnum.PSE, "Erro ao obter as Formas de Pagamento do Fornecedor.", WSIntegracaoStatusEnum.ATENCAO, ex, false);
        }
        return new SimulacaoRQ();
    }
    
    private Simulation montarSimulation(WSIntegrador integrador, IntegrationCodeRS integrationCode, WSPagtoMeioFinanciamento pagtoMeioFinanciamento, WSReserva reserva) throws ErrorException{
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
            
            
                    
            simulation = new Simulation();
            simulation.setProductCode(integrationCode.getCode());
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
}
