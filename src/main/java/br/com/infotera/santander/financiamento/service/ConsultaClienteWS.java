package br.com.infotera.santander.financiamento.service;

import br.com.infotera.common.ErrorException;
import br.com.infotera.common.WSCliente;
import br.com.infotera.common.WSIntegrador;
import br.com.infotera.common.enumerator.WSIntegracaoStatusEnum;
import br.com.infotera.common.enumerator.WSMensagemErroEnum;
import br.com.infotera.common.pagto.WSPagtoFinanciamento;
import br.com.infotera.common.pagto.financiamento.rqrs.WSPagtoConsultaClienteRQ;
import br.com.infotera.common.pagto.financiamento.rqrs.WSPagtoConsultaClienteRS;
import br.com.infotera.common.pagto.financiamento.rqrs.WSPagtoFinanciamentoRQ;
import br.com.infotera.common.util.Utils;
import br.com.infotera.santander.financiamento.client.SantanderClient;
import br.com.infotera.santander.financiamento.model.Purchase;
import br.com.infotera.santander.financiamento.rqrs.IntegrationCodeRS;
import br.com.infotera.santander.financiamento.rqrs.PreAnaliseRQ;
import br.com.infotera.santander.financiamento.rqrs.PreAnaliseRS;
import br.com.infotera.santander.financiamento.util.UtilsWS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsultaClienteWS {

//    @Autowired
//    private SessaoWS sessaoWS;
//
//    @Autowired
//    private SantanderClient santanderClient;
//
//    public WSPagtoConsultaClienteRS consultaCliente(WSPagtoConsultaClienteRQ consultaClienteRQ) throws ErrorException {
//        WSIntegrador integrador = null;
//        if (consultaClienteRQ.getIntegrador().getSessao() == null) {
//            integrador = sessaoWS.abreSessao(consultaClienteRQ.getIntegrador());
//        } else {
//            integrador = consultaClienteRQ.getIntegrador();
//        }
//
//        WSPagtoConsultaClienteRS wsPagtoConsultaClienteRS = new WSPagtoConsultaClienteRS();
//        wsPagtoConsultaClienteRS.setPagtoFinanciamento(montaPreAnalise(integrador, consultaClienteRQ));
//
//        return wsPagtoConsultaClienteRS;
//    }
//
//    private WSPagtoFinanciamento montaPreAnalise(WSIntegrador integrador, WSPagtoConsultaClienteRQ consultaClienteRQ) throws ErrorException {
//        // Obtem dados do cliente para requisição
////        Customer customer = montaCustomer(integrador, consultaClienteRQ);
//
//        // Obtem dados da reserva e do conector
//        Purchase purchase = montaPurchase(integrador, consultaClienteRQ);
//
//        // Realiza a chamada a Pré-Analise com dados do cliente e insumo
////        PreAnaliseRS preAnaliseRS = chamarPreAnalise(integrador, customer, purchase);
//
//        WSPagtoFinanciamento pagtoFinanciamento = new WSPagtoFinanciamento();
//        pagtoFinanciamento.setCliente(consultaClienteRQ.getCliente());
////        pagtoFinanciamento.setDtPrimeiroVencimento(Utils.toDate(preAnaliseRS.getAtxResponse().getFirstInstallment().getRecommendedDate(), "yyyy-MM-dd"));
////        pagtoFinanciamento.setVlEntrada(preAnaliseRS.getAtxResponse().getEntry().getRecommendedValue());
//
//        return null;
//    }
//
////    private PreAnaliseRS chamarPreAnalise(WSIntegrador integrador, Customer customer, Purchase purchase) throws ErrorException {
////        PreAnaliseRQ preAnaliseRQ = null;
////
////        // Obter o código da loja (ID Loja)
////        IntegrationCodeRS idTab = santanderClient.identificarTab(integrador);
////
////        if(customer != null && purchase != null && idTab != null){
////            preAnaliseRQ.setCustomer(customer);
////            preAnaliseRQ.setPurchase(purchase);
////            preAnaliseRQ.setUuid(idTab.getUuid().toString());
////        }
////
////        return santanderClient.preAnalise(integrador, preAnaliseRQ);
////    }
//
//    private Purchase montaPurchase(WSIntegrador integrador, WSPagtoConsultaClienteRQ consultaClienteRQ) throws ErrorException {
//        Purchase purchase = null;
//        try {
//            purchase.setFinancedObject("MO"); // PARA TESTES FOI SETADO VALOR FIXO AO INVÉS DE REALIZAR CHAMADA GET PARA OBTER O ID
////            purchase.setEntryValue(consultaClienteRQ.getReserva()); // Valor de Entrada
// //           purchase.setInstallmentValue(consultaClienteRQ.getPagtoFinanciamento().getVlDemaisParcelas()); // Valor das Parcelas
////            purchase.setInstallmentQuantity(consultaClienteRQ.getPagtoFinanciamento().getQtParcelas()); // EXISTE UMA CHAMADA GET A FIM DE OBTER O VALOR MÁXIMO DE PARCELAS
////            purchase.setTravelDate();  RETORNAR A DATA DE VIAGEM (OBRIGATÓRIO - TURISMO) - VERIFICAR COMO OBTER O WSRESERVA PARA COLETAR INFORMAÇÕES
//            purchase.setSubsegment(0); // NÃO EXISTE NA DOC REFERENCIA PARA REALIZAR A CHAMADA GET PARA OBTER O CÓDIGO A SER INFORMADO - AVISAR SANTANDER
////            purchase.setValue(consultaClienteRQ.getPagtoFinanciamento().getVlFinanciado());
//        } catch (Exception ex) {
//            throw new ErrorException(integrador, SimularWS.class, "montaPurchase", WSMensagemErroEnum.ADI, "Erro ao obter os valores para realizar a Pré-Analise.", WSIntegracaoStatusEnum.ATENCAO, ex);
//        }
//
//        return purchase;
//    }

//    private Customer montaCustomer(WSIntegrador integrador, WSPagtoConsultaClienteRQ consultaClienteRQ) throws ErrorException {
//        WSCliente cliente = null;
//        if(UtilsWS.verificarCliente(integrador, consultaClienteRQ.getCliente())){
//            cliente = consultaClienteRQ.getCliente();
//        }
//
//        return new Customer(Utils.formatData(cliente.getDtNascimento(), "yyyy-MM-dd'T'HH:mm:ssz"),
//                cliente.getDocumento().getNrDocumento(),
//                new CellPhone(cliente.getTelefone().getNrDDD(), cliente.getTelefone().getNrTelefone()));
//    }
}
