package br.com.infotera.santander.service;

import br.com.infotera.common.ErrorException;
import br.com.infotera.common.WSCliente;
import br.com.infotera.common.WSIntegrador;
import br.com.infotera.common.enumerator.WSIntegracaoStatusEnum;
import br.com.infotera.common.enumerator.WSMensagemErroEnum;
import br.com.infotera.common.pagto.WSPagtoBoleto;
import br.com.infotera.common.pagto.WSPagtoCartaoDebito;
import br.com.infotera.common.pagto.WSPagtoForma;
import br.com.infotera.common.pagto.WSPagtoMeioFinanciamento;
import br.com.infotera.common.pagto.financiamento.WSFinanciamentoParcelas;
import br.com.infotera.common.pagto.financiamento.rqrs.WSPagtoAnaliseRQ;
import br.com.infotera.common.pagto.financiamento.rqrs.WSPagtoAnaliseRS;
import br.com.infotera.common.util.Utils;
import br.com.infotera.santander.client.SantanderClient;
import br.com.infotera.santander.model.*;
import br.com.infotera.santander.model.RQRS.*;
import br.com.infotera.santander.util.UtilsWS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class SimularWS {

//    @Autowired
//    private SessaoWS sessaoWS;
//
//    @Autowired
//    private SantanderClient santanderClient;
//
//    public WSPagtoAnaliseRS simularFinanciamento(WSPagtoAnaliseRQ pagtoAnaliseRQ) throws ErrorException {
//        WSIntegrador integrador = null;
//        if (pagtoAnaliseRQ.getIntegrador().getSessao() == null) {
//            integrador = sessaoWS.abreSessao(pagtoAnaliseRQ.getIntegrador());
//        } else {
//            integrador = pagtoAnaliseRQ.getIntegrador();
//        }
//        
//        // Chamada para obter o Código da Loja (ID Loja) - Deve ser realizada nova chamada para obter novo código, caso seja feita nova análise
//        IntegrationCodeRS integrationCode = santanderClient.identificarTab(integrador);
//        
//        // Retorna a quantidade máxima de parcelas para o estabelecimento (StoreId) (VERIFICAR SE SERÁ ENVIADO NO MESMO PARÂMETRO OU SE SERÁ CRIADO OUTRO)
//        InstallmentAmountRS parcelaMax = santanderClient.retornarParcelas(integrador, integrationCode.getTabId());
//        pagtoAnaliseRQ.getPagtoMeioFinanciamento().setQtdParcelas(Integer.parseInt(parcelaMax.getDescription()));
//        
//        // Retorna os tipos de pagamentos permitidos para o cliente
////        List<WSPagtoForma> pagtoFormaList = retornaFormasPagto(integrador);
////        pagtoAnaliseRQ.getPagtoMeioFinanciamento().setFormasPagtoList(pagtoFormaList);
//
//        WSPagtoMeioFinanciamento pagtoMeioFinanciamento = montaPagtoMeioFinanciamento(integrador, integrationCode, pagtoAnaliseRQ);
//
//        return new WSPagtoAnaliseRS(integrador, pagtoMeioFinanciamento, pagtoAnaliseRQ.getFinanciamentoCadastro().getCliente(), WSIntegracaoStatusEnum.OK);
//    }
//
//    private WSPagtoMeioFinanciamento montaPagtoMeioFinanciamento(WSIntegrador integrador, IntegrationCodeRS integrationCode, WSPagtoAnaliseRQ pagtoAnaliseRQ) throws ErrorException {
//        // Obtem dados do cliente para requisição
//        Customer customer = montaCustomer(integrador, pagtoAnaliseRQ.getFinanciamentoCadastro().getCliente());
//        
//        // Obtem dados da reserva e do conector
//        Purchase purchase = montaPurchase(integrador, integrationCode, pagtoAnaliseRQ.getPagtoMeioFinanciamento());
//        if(pagtoAnaliseRQ.getReserva().getReservaAereo() != null){
//            purchase.setTravelDate(Utils.formatData(pagtoAnaliseRQ.getReserva().getReservaAereo().getVooList().get(0).getDtPartida(), "yyyy-MM-dd"));
//        }
//
//        // Realiza a chamada a Pré-Analise com dados do cliente e insumo a fim de avaliar as possibilidades de financiamento
//        PreAnaliseRS preAnaliseRS = chamarPreAnalise(integrador, customer, purchase, integrationCode);
//        
//        // Apresentar as informações sobre datas e valores limites (VERIFICAR SE SERÁ ENVIADO NO MESMO OBJETO OU SE SERÁ CRIADO OUTRO)
//        pagtoAnaliseRQ.getPagtoMeioFinanciamento().setDtMinEntrada(Utils.toDate(preAnaliseRS.getAtxResponse().getFirstInstallment().getMinimumDate(), "yyyy-MM-dd"));
//        pagtoAnaliseRQ.getPagtoMeioFinanciamento().setDtMaxEntrada(Utils.toDate(preAnaliseRS.getAtxResponse().getFirstInstallment().getMaximumDate(), "yyyy-MM-dd"));
//        pagtoAnaliseRQ.getPagtoMeioFinanciamento().setVlMinEntrada(preAnaliseRS.getAtxResponse().getEntry().getMinimumValue());
//        pagtoAnaliseRQ.getPagtoMeioFinanciamento().setVlMaxEntrada(preAnaliseRS.getAtxResponse().getEntry().getMaximumValue());
//        
//        // Realiza a chamada a Simulação com dados retornados pela Pré-Análise
//        SimulacaoRS simulacaoRS = chamarSimulacao(integrador, integrationCode, customer, preAnaliseRS, pagtoAnaliseRQ);
//
//        WSPagtoMeioFinanciamento pagtoMeioFinanciamento = null;
//        if(preAnaliseRS != null) {
//            try {
//                List<WSFinanciamentoParcelas> parcelasList = new ArrayList<>();
//                preAnaliseRS.getAtxResponse().getTerms().stream()
//                        .map(t -> {
//                            WSFinanciamentoParcelas parcela = new WSFinanciamentoParcelas();
//                            parcela.setNrParcela(t.getTimes());
//                            return parcela;
//                        }).forEachOrdered((parcela) -> {
//                            parcelasList.add(parcela);
//                        });
//
//                List<WSPagtoForma> formaPagtoList = new ArrayList<>();
//                preAnaliseRS.getAtxResponse().getPaymentForms().stream()
//                        .map(p -> {
//                            WSPagtoForma formaPagto = null;
//                            if(p.getIntegrationCode().equals("CA")){
//                                WSPagtoBoleto boleto = new WSPagtoBoleto();
//                                boleto.setCdBoleto(String.valueOf(p.getId()));
//                                boleto.setCdEspecie(p.getDescription());
//                                formaPagto = (WSPagtoForma) boleto;
//                            } else if(p.getIntegrationCode().equals("DC")){
//                                WSPagtoCartaoDebito debito = new WSPagtoCartaoDebito();
//                                debito.setIdExterno(String.valueOf(p.getId()));
//                                debito.setDsPagamento(p.getDescription());
//                                formaPagto = (WSPagtoForma) debito;
//                            }
//                            return formaPagto;
//                        }).forEachOrdered( forma -> {
//                            formaPagtoList.add(forma);
//                        });
//
//                pagtoMeioFinanciamento = new WSPagtoMeioFinanciamento();
//                pagtoMeioFinanciamento.setDtPrimeiroVencimento(Utils.toDate(simulacaoRS.getAtxResponse().getFirstInstallment().getRecommendedDate(), "yyyy-MM-dd"));
//                pagtoMeioFinanciamento.setDtMinEntrada(Utils.toDate(simulacaoRS.getAtxResponse().getFirstInstallment().getMinimumDate(), "yyyy-MM-dd"));
//                pagtoMeioFinanciamento.setDtMaxEntrada(Utils.toDate(simulacaoRS.getAtxResponse().getFirstInstallment().getMaximumDate(), "yyyy-MM-dd"));
//                pagtoMeioFinanciamento.setVlEntrada(simulacaoRS.getAtxResponse().getEntry().getRecommendedValue());
//                pagtoMeioFinanciamento.setVlMinEntrada(simulacaoRS.getAtxResponse().getEntry().getMinimumValue());
//                pagtoMeioFinanciamento.setVlMaxEntrada(simulacaoRS.getAtxResponse().getEntry().getMaximumValue());
//                pagtoMeioFinanciamento.setCorrentista(simulacaoRS.getAtxResponse().getAccountHolder());
//                pagtoMeioFinanciamento.setIndicadorTaxaCredito(simulacaoRS.getAtxResponse().getFees().getTcExemption());
//                
//                pagtoMeioFinanciamento.setPagtoFormaEntrada(verificaFormaPagtoCliente(integrador, preAnaliseRS));
//
//                pagtoMeioFinanciamento.setCdAutorizacao(preAnaliseRS.getId() + "#"
//                                                    + preAnaliseRS.getAtxResponse().getPriceTable() + "#"
//                                                    + preAnaliseRS.getAtxResponse().getFees().getTabFee() + "#"
//                                                    + preAnaliseRS.getUuid().toString());
//
//            } catch (Exception ex) {
//                throw new ErrorException(integrador, SimularWS.class, "montaPagtoFinanciamento", 
//                        WSMensagemErroEnum.ADI, "Erro ao obter os valores para o PagtoFinanciamento.", WSIntegracaoStatusEnum.ATENCAO, ex);
//            }
//        }
//
//        return pagtoMeioFinanciamento;
//    }
//
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
////    private List<WSPagtoForma> retornaFormasPagto(WSIntegrador integrador) throws ErrorException {
////        List<ProductCodeRS> productCodeRSList = santanderClient.retornarProductCode(integrador);
////        
////        List<WSPagtoForma> pagtoFormaList = new ArrayList<>();
////        productCodeRSList.stream()
////                .map(pc -> {
////                    WSPagtoForma formaPagto = null;
////                    if(pc.getDescription().contains("BOLETO")){
////                        WSPagtoBoleto boleto = new WSPagtoBoleto();
////                        boleto.setIdExterno(String.valueOf(pc.getId()));
////                        boleto.setDsPagamento(pc.getDescription());
////                        formaPagto = (WSPagtoForma) boleto;
////                    } else {
////                        WSPagtoCartaoDebito debito = new WSPagtoCartaoDebito();
////                        debito.setIdExterno(String.valueOf(pc.getId()));
////                        debito.setDsPagamento(pc.getDescription());
////                        formaPagto = (WSPagtoForma) debito;
////                    }
////                    return formaPagto;
////                }).forEachOrdered( forma -> {
////                    pagtoFormaList.add(forma);
////                });
////           
////        return pagtoFormaList != null && !Utils.isListNothing(pagtoFormaList) ? pagtoFormaList : null;
////    }
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
}
