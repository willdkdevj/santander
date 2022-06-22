/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infotera.santander.getnet.services;

import br.com.infotera.common.ErrorException;
import br.com.infotera.common.WSCliente;
import br.com.infotera.common.WSIntegrador;
import br.com.infotera.common.WSSessao;
import br.com.infotera.common.enumerator.WSIntegracaoStatusEnum;
import br.com.infotera.common.enumerator.WSIntegradorEnum;
import br.com.infotera.common.enumerator.WSMensagemErroEnum;
import br.com.infotera.common.enumerator.WSPagtoCartaoStatusEnum;
import br.com.infotera.common.pagto.WSPagtoCartao;
import br.com.infotera.common.pagto.rqrs.WSPagtoCartaoRQ;
import br.com.infotera.common.pagto.rqrs.WSPagtoCartaoRS;
import br.com.infotera.common.pagto.threeds.WSThreeDS;
import br.com.infotera.common.pagto.threeds.WSThreeDSToken;
import br.com.infotera.common.util.Utils;
import br.com.infotera.santander.getnet.client.GetNetClient;
import br.com.infotera.santander.getnet.model.AuthenticateRequest;
import br.com.infotera.santander.getnet.model.AuthorizationRequest;
import br.com.infotera.santander.getnet.model.AuthorizationResponse;
import br.com.infotera.santander.getnet.model.CancelRequest;
import br.com.infotera.santander.getnet.model.CancelResponse;
import br.com.infotera.santander.getnet.model.CaptureRequest;
import br.com.infotera.santander.getnet.model.CaptureResponse;
import br.com.infotera.santander.getnet.model.CardNumberTokenRequest;
import br.com.infotera.santander.getnet.model.CardNumberTokenResponse;
import br.com.infotera.santander.getnet.model.CreditRequest;
import br.com.infotera.santander.getnet.model.ThreeDSAuthenticationRequest;
import br.com.infotera.santander.getnet.model.ThreeDSAuthenticationResponse;
import br.com.infotera.santander.getnet.model.ThreeDSAuthenticationResultRequest;
import br.com.infotera.santander.getnet.model.ThreeDSAuthenticationResultResponse;
import br.com.infotera.santander.getnet.model.ThreeDSAuthorizationRequest;
import br.com.infotera.santander.getnet.model.ThreeDSAuthorizationResponse;
import br.com.infotera.santander.getnet.model.ThreeDsTokenRequest;
import br.com.infotera.santander.getnet.model.ThreeDsTokenResponse;
import br.com.infotera.santander.getnet.model.sdk.Amount;
import br.com.infotera.santander.getnet.model.sdk.Authentication;
import br.com.infotera.santander.getnet.model.sdk.Cancel;
import br.com.infotera.santander.getnet.model.sdk.Card;
import br.com.infotera.santander.getnet.model.sdk.Customer;
import br.com.infotera.santander.getnet.model.sdk.Order;
import br.com.infotera.santander.getnet.model.sdk.PersonalIdentification;
import br.com.infotera.santander.getnet.model.sdk.Shippings;
import br.com.infotera.santander.getnet.util.UtilsWS;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author enioj
 */
@Component
public class MontaWS {

    @Autowired
    private Gson gson;
    @Autowired
    private GetNetClient getNetClient;

    private void autenticar(WSIntegrador integrador) throws ErrorException {
        AuthenticateRequest acessoRq = new AuthenticateRequest("oob", "client_credentials");
        WSSessao sessao = getNetClient.authenticate(integrador, acessoRq);
        integrador.setSessao(sessao);
    }

    private boolean getIsStThreeDS(WSIntegrador integrador, WSPagtoCartao pagtoCartao) {
        Map<String, String> map = WSIntegradorEnum.getDsCredencialMap(WSIntegradorEnum.GETNET.getId(), integrador.getDsCredencialList().stream().collect(Collectors.joining(",")));
        String stThreeDS = map.get("Autorização 3DS? (preencher com SIM ou NAO)");

        return "SIM".equals(stThreeDS) && (pagtoCartao.getDsMensagem() != null && "3DS".equals(pagtoCartao.getDsMensagem()));
    }

    private CardNumberTokenResponse gerarTokenCartao(WSIntegrador integrador, WSPagtoCartao cartaoRQ) throws ErrorException {

        CardNumberTokenRequest cardNumberTokenRequest = new CardNumberTokenRequest(cartaoRQ.getNrCartao(), cartaoRQ.getIdTransacao());

        return getNetClient.generateCardNumberToken(integrador, cardNumberTokenRequest);
    }

    public WSPagtoCartaoRS autorizar(WSPagtoCartaoRQ cartaoRQ) throws ErrorException {

        if (getIsStThreeDS(cartaoRQ.getIntegrador(), cartaoRQ.getPagtoCartao())) {
            return autorizarThreeDS(cartaoRQ);
        }

        //Autenticacão para obter token que será usada nas demais chamadas
        WSIntegrador integrador = cartaoRQ.getIntegrador();
        autenticar(integrador);
        //[FIM]Autenticacão para obter token que será usada nas demais chamadas

        //Para efetuar transações com segurança, é gerado um token do cartão, o mesmo será utilizado
        CardNumberTokenResponse cartaoTokenRS = gerarTokenCartao(integrador, cartaoRQ.getPagtoCartao());
        //[FIM]Para efetuar transações com segurança, é gerado um token do cartão, o mesmo será utilizado

        Customer customer = UtilsWS.montaCustomer(cartaoRQ.getPagtoCartao().getIdTransacao(), cartaoRQ.getPagtoCartao().getCliente());

        Shippings shippings = UtilsWS.montaShippings(cartaoRQ.getPagtoCartao().getCliente());

        Order order = new Order(cartaoRQ.getPagtoCartao().getIdTransacao(), 0, "service");

        //Verifica se o pagamento é feito em 1x somente ou em mais de uma(sem juros)
        String tpTransacao = null;
        if (cartaoRQ.getPagtoCartao().getQtParcela() == 1) {
            tpTransacao = "FULL";
        } else {
            tpTransacao = "INSTALL_NO_INTEREST";
        }
        //[FIM]Verifica se o pagamento é feito em 1x somente ou em mais de uma(sem juros)

        //Verifica se a data de vencimento do cartão é menor que a data atual
        Date dataExpiracao = Utils.toDate(cartaoRQ.getPagtoCartao().getAaValidade() + "-" + cartaoRQ.getPagtoCartao().getMmValidade() + "-01", "yyyy-MM-dd");
        if (dataExpiracao.before(new Date())) {
            throw new ErrorException(integrador, MontaWS.class, "autorizar", WSMensagemErroEnum.GENCONEC, "Cartão Vencido", WSIntegracaoStatusEnum.NEGADO, null);
        }
        //[FIM]Verifica se a data de vencimento do cartão é menor que a data atual

        Card card = UtilsWS.montaCard(cartaoTokenRS.getNumber_token(), cartaoRQ.getPagtoCartao());

        CreditRequest credit = new CreditRequest(true,
                false,
                false,
                false,
                tpTransacao,
                cartaoRQ.getPagtoCartao().getQtParcela(),
                null,
                null, //dynamicMCC
                card);

        //Valor a ser enviado na api deve ser em centavos, ou seja, sem as vírgulas
        String valor = Utils.formatDouble(cartaoRQ.getPagtoCartao().getVlPagamento(), 2, 2).replace(".", "").replace(",", "");
        int valorPagto = Integer.parseInt(valor);

        //Autorizar uma transação, dados recebidos dados para pagamento com cartão de crédito
        AuthorizationRequest autorizacaoRQ = new AuthorizationRequest(integrador.getDsCredencialList().get(0),
                valorPagto,
                "BRL",
                order,
                customer,
                credit);

        autorizacaoRQ.setShippings(Arrays.asList(shippings));

        AuthorizationResponse autorizacaoRS = getNetClient.authorize(integrador, autorizacaoRQ);
        //[FIM]Autorizar uma transação, dados recebidos dados para pagamento com cartão de crédito

        WSPagtoCartaoStatusEnum statusCartao = null;

        if (autorizacaoRS.getStatus().contains("AUTHORIZED")) {
            statusCartao = WSPagtoCartaoStatusEnum.AUTORIZADO;
        } else {
            statusCartao = WSPagtoCartaoStatusEnum.NEGADO;
        }

        WSPagtoCartao pagtoCartao = new WSPagtoCartao(cartaoRQ.getPagtoCartao().getCartaoTipo(),
                cartaoRQ.getPagtoCartao().getNmTitular(),
                autorizacaoRS.getCredit().getTerminal_nsu(),//nrAprovacao
                autorizacaoRS.getCredit().getAuthorization_code(),//nrAutorizacao
                cartaoRQ.getPagtoCartao().getNrCartao(),
                cartaoRQ.getPagtoCartao().getCdSeguranca(),
                cartaoRQ.getPagtoCartao().getMmValidade(),
                cartaoRQ.getPagtoCartao().getAaValidade(),
                cartaoRQ.getPagtoCartao().getVlPagamento(),//vlpagamento
                cartaoRQ.getPagtoCartao().getQtParcela(),
                statusCartao,
                autorizacaoRS.getSeller_id(),//idexterno
                new Date(),//dtTransacao
                autorizacaoRS.getStatus(),//dsmensagem
                cartaoTokenRS.getNumber_token(),//idToken
                autorizacaoRS.getPayment_id()
        );//idtoken

        return new WSPagtoCartaoRS(integrador, pagtoCartao, WSIntegracaoStatusEnum.OK);
    }

    public WSPagtoCartaoRS capturar(WSPagtoCartaoRQ cartaoRQ) throws ErrorException {

        if (getIsStThreeDS(cartaoRQ.getIntegrador(), cartaoRQ.getPagtoCartao())) {
            cartaoRQ.getPagtoCartao().setCartaoStatus(WSPagtoCartaoStatusEnum.CAPTURADO);
            return new WSPagtoCartaoRS(cartaoRQ.getIntegrador(), cartaoRQ.getPagtoCartao(), WSIntegracaoStatusEnum.OK);
        }

        WSPagtoCartaoStatusEnum statusCartao = null;
        WSPagtoCartao pagtoCartao = null;

        //Autenticacão para obter token que será usada nas demais chamadas
        WSIntegrador integrador = cartaoRQ.getIntegrador();
        autenticar(integrador);
        //[FIM]Autenticacão para obter token que será usada nas demais chamadas

        //Valor a ser enviado na api deve ser em centavos, ou seja, sem as vírgulas
        String valor = Utils.formatDouble(cartaoRQ.getPagtoCartao().getVlPagamento(), 2, 2).replace(".", "").replace(",", "");
        int valorPagto = Integer.parseInt(valor);

        //Captura de pagamento
        CaptureRequest captura = new CaptureRequest(new Amount(valorPagto), cartaoRQ.getPagtoCartao().getNrTid());
        CaptureResponse capturaRS = null;

//        if (getIsStThreeDS(integrador)) {
//            captura.getAmount().setPayment_method("CREDIT_AUTHORIZATION");;
//            capturaRS = getNetClient.capturaThreeDS(cartaoRQ.getIntegrador(), captura);
//        } else {
        capturaRS = getNetClient.captura(cartaoRQ.getIntegrador(), captura);
//        }

        //[FIM]Captura de pagamento
        if (capturaRS.getStatus().equalsIgnoreCase("CONFIRMED")) {
            statusCartao = WSPagtoCartaoStatusEnum.CAPTURADO;

            pagtoCartao = new WSPagtoCartao(cartaoRQ.getPagtoCartao().getCartaoTipo(),
                    cartaoRQ.getPagtoCartao().getNmTitular(),
                    cartaoRQ.getPagtoCartao().getNrAprovacao(),//nrAprovacao
                    cartaoRQ.getPagtoCartao().getNrAutorizacao(),//nrAutorizacao
                    cartaoRQ.getPagtoCartao().getNrCartao(),
                    cartaoRQ.getPagtoCartao().getCdSeguranca(),
                    cartaoRQ.getPagtoCartao().getMmValidade(),
                    cartaoRQ.getPagtoCartao().getAaValidade(),
                    cartaoRQ.getPagtoCartao().getVlPagamento(),//vlpagamento
                    cartaoRQ.getPagtoCartao().getQtParcela(),
                    statusCartao,
                    capturaRS.getSeller_id(),//idexterno
                    new Date(),//dtTransacao
                    capturaRS.getStatus(),//dsmensagem
                    null,
                    capturaRS.getPayment_id()
            );//idtoken

        } else {
            statusCartao = WSPagtoCartaoStatusEnum.NEGADO;

            if (capturaRS.getStatus().equalsIgnoreCase("")) {
                statusCartao = WSPagtoCartaoStatusEnum.CANCELADO;
            }

            String nrAutorizacao = null;

            pagtoCartao = new WSPagtoCartao(cartaoRQ.getPagtoCartao().getCartaoTipo(),
                    cartaoRQ.getPagtoCartao().getNmTitular(),
                    cartaoRQ.getPagtoCartao().getNrAprovacao(),//nrAprovacao
                    nrAutorizacao,//nrAutorizacao
                    cartaoRQ.getPagtoCartao().getNrCartao(),
                    cartaoRQ.getPagtoCartao().getCdSeguranca(),
                    cartaoRQ.getPagtoCartao().getMmValidade(),
                    cartaoRQ.getPagtoCartao().getAaValidade(),
                    cartaoRQ.getPagtoCartao().getVlPagamento(),//vlpagamento
                    cartaoRQ.getPagtoCartao().getQtParcela(),
                    statusCartao,
                    capturaRS.getSeller_id(),//idexterno
                    new Date(),//dtTransacao
                    capturaRS.getPayment_id(),
                    capturaRS.getPayment_id()
            );

        }

        return new WSPagtoCartaoRS(cartaoRQ.getIntegrador(), pagtoCartao, WSIntegracaoStatusEnum.OK);
    }

    public WSPagtoCartaoRS cancelar(WSPagtoCartaoRQ cartaoRQ) throws ErrorException {

        WSPagtoCartaoStatusEnum cartaoStatus = null;
        String dsMensagem = null;

        //Autenticacão para obter token que será usada nas demais chamadas
        WSIntegrador integrador = cartaoRQ.getIntegrador();
        autenticar(integrador);
        //[FIM]Autenticacão para obter token que será usada nas demais chamadas

        //Valor a ser enviado na api deve ser em centavos, ou seja, sem as vírgulas
        String valor = Utils.formatDouble(cartaoRQ.getPagtoCartao().getVlPagamento(), 2, 2).replace(".", "").replace(",", "");
        int valorPagto = Integer.parseInt(valor);

        boolean diaMaisN = false;
        if (Utils.diferencaEmDias(cartaoRQ.getPagtoCartao().getDtTransacao(), new Date()) > 0) {
            diaMaisN = true;
        }

        //Faz chamada de cancelamento
        CancelResponse cancelamentoRS = null;
        CancelRequest cancelamentoRQ = null;
        if (getIsStThreeDS(integrador, cartaoRQ.getPagtoCartao())) {
            Cancel cancelamento = new Cancel("CREDIT");
            cancelamentoRQ = new CancelRequest(cancelamento, false);
            cancelamentoRQ.setPayment_id(cartaoRQ.getPagtoCartao().getNrTid());
            cancelamentoRS = getNetClient.cancelarThreeDS(cartaoRQ.getIntegrador(), cancelamentoRQ);
            diaMaisN = false;
        } else {
            Cancel cancelamento = new Cancel(cartaoRQ.getPagtoCartao().getNrTid(), valorPagto, null);
            cancelamentoRQ = new CancelRequest(cancelamento, diaMaisN);
            cancelamentoRS = getNetClient.cancelar(cartaoRQ.getIntegrador(), cancelamentoRQ);
        }

        //[FIM]Faz chamada de cancelamento
        if (diaMaisN) {

            if (cancelamentoRS.getStatus().equalsIgnoreCase("ACCEPTED")) {
                cartaoStatus = WSPagtoCartaoStatusEnum.CANCELADO;
                dsMensagem = "Cancelado em: " + cancelamentoRS.getCancel_request_at() + " - CancelID: " + cancelamentoRS.getCancel_request_id();
            }

        } else {

            if (cancelamentoRS.getStatus().equalsIgnoreCase("CANCELED")) {
                cartaoStatus = WSPagtoCartaoStatusEnum.CANCELADO;
                dsMensagem = cancelamentoRS.getStatus();
            }
        }

        WSPagtoCartao pagtoCartao = new WSPagtoCartao(cartaoStatus, dsMensagem);

        return new WSPagtoCartaoRS(cartaoRQ.getIntegrador(), pagtoCartao, WSIntegracaoStatusEnum.OK);
    }

    public WSPagtoCartaoRS autorizarComToken(WSPagtoCartaoRQ cartaoRQ) throws ErrorException {

        //Autenticacão para obter token que será usada nas demais chamadas
        WSIntegrador integrador = cartaoRQ.getIntegrador();
        autenticar(integrador);
        //[FIM]Autenticacão para obter token que será usada nas demais chamadas

        //Para efetuar transações com segurança, é gerado um token do cartão, o mesmo será utilizado
        CardNumberTokenResponse cartaoTokenRS = gerarTokenCartao(integrador, cartaoRQ.getPagtoCartao());
        //[FIM]Para efetuar transações com segurança, é gerado um token do cartão, o mesmo será utilizado

        WSPagtoCartao pagtoCartao = new WSPagtoCartao();
        pagtoCartao.setIdToken(cartaoTokenRS.getNumber_token());
        pagtoCartao.setCartaoStatus(WSPagtoCartaoStatusEnum.AUTORIZADO);
        pagtoCartao.setNrCartao(String.join("", Collections.nCopies(cartaoRQ.getPagtoCartao().getNrCartao().length() - 4, "*")) + cartaoRQ.getPagtoCartao().getNrCartao().substring(cartaoRQ.getPagtoCartao().getNrCartao().length() - 4));

        return new WSPagtoCartaoRS(cartaoRQ.getIntegrador(), pagtoCartao, WSIntegracaoStatusEnum.OK);
    }

    public WSPagtoCartaoRS autenticarThreeDS(WSPagtoCartaoRQ cartaoRQ) throws ErrorException {

        //Autenticacão para obter token que será usada nas demais chamadas
        WSIntegrador integrador = cartaoRQ.getIntegrador();
        autenticar(integrador);
        //[FIM]Autenticacão para obter token que será usada nas demais chamadas

        WSPagtoCartao pagtoCartao = new WSPagtoCartao();
        pagtoCartao.setThreeDS(new WSThreeDS(new WSThreeDSToken(integrador.getSessao().getDsSessao())));

        return new WSPagtoCartaoRS(cartaoRQ.getIntegrador(), pagtoCartao, WSIntegracaoStatusEnum.OK);
    }

    public WSPagtoCartaoRS gerarTokenThreeDS(WSPagtoCartaoRQ cartaoRQ) throws ErrorException {

        //Autenticacão para obter token que será usada nas demais chamadas
        WSIntegrador integrador = cartaoRQ.getIntegrador();
        autenticar(integrador);
        //[FIM]Autenticacão para obter token que será usada nas demais chamadas

        String valor = Utils.formatDouble(cartaoRQ.getPagtoCartao().getVlPagamento(), 2, 2).replace(".", "").replace(",", "");
        int valorPagto = Integer.parseInt(valor);

        ThreeDsTokenRequest threeDsTokenRequest = new ThreeDsTokenRequest(
                cartaoRQ.getPagtoCartao().getIdTransacao(),
                cartaoRQ.getPagtoCartao().getSgMoeda(),
                cartaoRQ.getPagtoCartao().getThreeDS().getVersion(),
                cartaoRQ.getPagtoCartao().getIdTransacao(),
                cartaoRQ.getPagtoCartao().getThreeDS().getOverridePaymentMethod(),
                valorPagto
        );

        ThreeDsTokenResponse threeDsTokenResponse = getNetClient.generateTokenThreeDS(integrador, threeDsTokenRequest);

        WSPagtoCartao pagtoCartao = new WSPagtoCartao();
        pagtoCartao.setThreeDS(new WSThreeDS(new WSThreeDSToken(threeDsTokenResponse.getAccess_token())));

        return new WSPagtoCartaoRS(cartaoRQ.getIntegrador(), pagtoCartao, WSIntegracaoStatusEnum.OK);
    }

    public WSPagtoCartaoRS preAutorizarThreeDS(WSPagtoCartaoRQ cartaoRQ) throws ErrorException {

        //Autenticacão para obter token que será usada nas demais chamadas
        WSIntegrador integrador = cartaoRQ.getIntegrador();
        autenticar(integrador);
        //[FIM]Autenticacão para obter token que será usada nas demais chamadas

        String valor = Utils.formatDouble(cartaoRQ.getPagtoCartao().getVlPagamento(), 2, 2).replace(".", "").replace(",", "");
        int valorPagto = Integer.parseInt(valor);

        WSPagtoCartao pagtoCartao = cartaoRQ.getPagtoCartao();
        WSThreeDS threeDS = pagtoCartao.getThreeDS();
        WSCliente cliente = pagtoCartao.getCliente();

        //Para efetuar transações com segurança, é gerado um token do cartão, o mesmo será utilizado
        CardNumberTokenResponse cartaoTokenRS = gerarTokenCartao(integrador, cartaoRQ.getPagtoCartao());
        //[FIM]Para efetuar transações com segurança, é gerado um token do cartão, o mesmo será utilizado

        String typeCard = null;

        switch (pagtoCartao.getCartaoTipo()) {
            case VISA:
                typeCard = "001";
                break;
            case MASTERCARD:
                typeCard = "002";
                break;
            case AMEX:
                typeCard = "003";
                break;
            case ELO:
                typeCard = "054";
                break;
            default:
                break;
        }

        ThreeDSAuthenticationRequest threeDSAuthenticationRequest = new ThreeDSAuthenticationRequest(
                pagtoCartao.getNmTitular(),
                threeDS.getOverridePaymentMethod(),
                null,
                new Authentication(threeDS.getToken().getToken(), pagtoCartao.getQtParcela(), null),
                UtilsWS.montaDevice(threeDS.getDevice()),
                new PersonalIdentification(cliente.getDocumento().getNrDocumento(), cliente.getDocumento().getDocumentoTipo().getNmTipo()),
                new Card(cartaoTokenRS.getNumber_token(), pagtoCartao.getMmValidade(), pagtoCartao.getAaValidade(), typeCard),
                UtilsWS.montaThreeDSOrder(pagtoCartao, valorPagto)
        );

        ThreeDSAuthenticationResponse threeDSAuthenticationResponse = getNetClient.authenticateThreeDS(integrador, threeDSAuthenticationRequest);

        JsonObject responseObject = new JsonObject();

        if (threeDSAuthenticationResponse.getClient_reference_information() != null) {
            JsonObject clientReferenceInformation = new JsonObject();
            clientReferenceInformation.addProperty("code", threeDSAuthenticationResponse.getClient_reference_information().getCode());
            responseObject.add("clientReferenceInformation", clientReferenceInformation);
        }

        if (threeDSAuthenticationResponse.getConsumer_authentication_information() != null) {
            JsonObject consumerAuthenticationInformation = new JsonObject();
            consumerAuthenticationInformation.addProperty("acsUrl", threeDSAuthenticationResponse.getConsumer_authentication_information().getAcs_url());
            consumerAuthenticationInformation.addProperty("authenticationPath", threeDSAuthenticationResponse.getConsumer_authentication_information().getAuthentication_path());
            consumerAuthenticationInformation.addProperty("authenticationTransactionId", threeDSAuthenticationResponse.getConsumer_authentication_information().getAuthentication_transaction_id());
            consumerAuthenticationInformation.addProperty("pareq", threeDSAuthenticationResponse.getConsumer_authentication_information().getPareq());
            consumerAuthenticationInformation.addProperty("proofXml", threeDSAuthenticationResponse.getConsumer_authentication_information().getProof_xml());
            consumerAuthenticationInformation.addProperty("proxyPan", threeDSAuthenticationResponse.getConsumer_authentication_information().getProxy_pan());
            consumerAuthenticationInformation.addProperty("specificationVersion", threeDSAuthenticationResponse.getConsumer_authentication_information().getSpecification_version());
            consumerAuthenticationInformation.addProperty("veresEnrolled", threeDSAuthenticationResponse.getConsumer_authentication_information().getVeres_enrolled());
            consumerAuthenticationInformation.addProperty("xid", threeDSAuthenticationResponse.getConsumer_authentication_information().getXid());
            consumerAuthenticationInformation.addProperty("token", threeDSAuthenticationResponse.getConsumer_authentication_information().getToken());

            consumerAuthenticationInformation.addProperty("authenticationResult", threeDSAuthenticationResponse.getConsumer_authentication_information().getAuthentication_result());
            consumerAuthenticationInformation.addProperty("authenticationStatusMsg", threeDSAuthenticationResponse.getConsumer_authentication_information().getAuthentication_status_msg());
            consumerAuthenticationInformation.addProperty("indicator", threeDSAuthenticationResponse.getConsumer_authentication_information().getIndicator());
            consumerAuthenticationInformation.addProperty("eci", threeDSAuthenticationResponse.getConsumer_authentication_information().getEci());
            consumerAuthenticationInformation.addProperty("paresStatus", threeDSAuthenticationResponse.getConsumer_authentication_information().getPares_status());
            consumerAuthenticationInformation.addProperty("ucafAuthenticationData", threeDSAuthenticationResponse.getConsumer_authentication_information().getUcaf_authentication_data());
            consumerAuthenticationInformation.addProperty("ucafCollectionIndicator", threeDSAuthenticationResponse.getConsumer_authentication_information().getUcaf_collection_indicator());
            consumerAuthenticationInformation.addProperty("ucaf", threeDSAuthenticationResponse.getConsumer_authentication_information().getUcaf());
            consumerAuthenticationInformation.addProperty("cavv", threeDSAuthenticationResponse.getConsumer_authentication_information().getCavv());
            consumerAuthenticationInformation.addProperty("cavvAlgorithm", threeDSAuthenticationResponse.getConsumer_authentication_information().getCavv_algorithm());

//            
            responseObject.add("consumerAuthenticationInformation", consumerAuthenticationInformation);
        }

        if (threeDSAuthenticationResponse.getError_information() != null) {
            JsonObject errorInformation = new JsonObject();
            errorInformation.addProperty("message", threeDSAuthenticationResponse.getError_information().getMessage());
            errorInformation.addProperty("reason", threeDSAuthenticationResponse.getError_information().getReason());
            responseObject.add("errorInformation", errorInformation);
        }

        if (threeDSAuthenticationResponse.getCard() != null) {
            JsonObject card = new JsonObject();
            card.addProperty("numberToken", threeDSAuthenticationResponse.getCard().getNumber_token());
            responseObject.add("card", card);

        }

        responseObject.addProperty("id", threeDSAuthenticationResponse.getId());
        responseObject.addProperty("status", threeDSAuthenticationResponse.getStatus());
        responseObject.addProperty("submitTimeUtc", threeDSAuthenticationResponse.getSubmit_time_utc());
        responseObject.addProperty("referenceId", threeDSAuthenticationResponse.getReference_id());
        responseObject.addProperty("orgUnitId", threeDSAuthenticationResponse.getOrg_unit_id());

        WSPagtoCartao pagtoCartaoRS = new WSPagtoCartao();
        pagtoCartaoRS.setThreeDS(new WSThreeDS(gson.toJson(responseObject)));
        return new WSPagtoCartaoRS(cartaoRQ.getIntegrador(), pagtoCartaoRS, WSIntegracaoStatusEnum.OK);
    }

    public WSPagtoCartaoRS consultaAutorizarThreeDS(WSPagtoCartaoRQ cartaoRQ) throws ErrorException {

        //Autenticacão para obter token que será usada nas demais chamadas
        WSIntegrador integrador = cartaoRQ.getIntegrador();
        autenticar(integrador);
        //[FIM]Autenticacão para obter token que será usada nas demais chamadas

        String valor = Utils.formatDouble(cartaoRQ.getPagtoCartao().getVlPagamento(), 2, 2).replace(".", "").replace(",", "");
        int valorPagto = Integer.parseInt(valor);

        WSPagtoCartao pagtoCartao = cartaoRQ.getPagtoCartao();
        WSThreeDS threeDS = pagtoCartao.getThreeDS();

        //Para efetuar transações com segurança, é gerado um token do cartão, o mesmo será utilizado
        CardNumberTokenResponse cartaoTokenRS = gerarTokenCartao(integrador, cartaoRQ.getPagtoCartao());
        //[FIM]Para efetuar transações com segurança, é gerado um token do cartão, o mesmo será utilizado

        String typeCard = null;

        switch (pagtoCartao.getCartaoTipo()) {
            case VISA:
                typeCard = "001";
                break;
            case MASTERCARD:
                typeCard = "002";
                break;
            case AMEX:
                typeCard = "003";
                break;
            case ELO:
                typeCard = "054";
                break;
            default:
                break;
        }

        ThreeDSAuthenticationResultRequest threeDSAuthenticationResultRequest = new ThreeDSAuthenticationResultRequest(
                pagtoCartao.getSgMoeda(),
                threeDS.getOverridePaymentMethod(),
                threeDS.getToken().getToken(),
                threeDS.getToken().getTokenChallenge(),
                valorPagto,
                new Card(cartaoTokenRS.getNumber_token(), pagtoCartao.getMmValidade(), pagtoCartao.getAaValidade(), typeCard)
        );

        ThreeDSAuthenticationResultResponse threeDSAuthenticationResponse = getNetClient.authenticationResultThreeDS(integrador, threeDSAuthenticationResultRequest);

        JsonObject responseObject = new JsonObject();

        JsonObject clientReferenceInformation = new JsonObject();
        clientReferenceInformation.addProperty("code", threeDSAuthenticationResponse.getClient_reference_information().getCode());
        responseObject.add("clientReferenceInformation", clientReferenceInformation);

        JsonObject consumerAuthenticationInformation = new JsonObject();
        consumerAuthenticationInformation.addProperty("authenticationResult", threeDSAuthenticationResponse.getConsumer_authentication_information().getAuthentication_result());
        consumerAuthenticationInformation.addProperty("authenticationStatusMsg", threeDSAuthenticationResponse.getConsumer_authentication_information().getAuthentication_status_msg());
        consumerAuthenticationInformation.addProperty("indicator", threeDSAuthenticationResponse.getConsumer_authentication_information().getIndicator());
        consumerAuthenticationInformation.addProperty("eci", threeDSAuthenticationResponse.getConsumer_authentication_information().getEci());
        consumerAuthenticationInformation.addProperty("paresStatus", threeDSAuthenticationResponse.getConsumer_authentication_information().getPares_status());
        consumerAuthenticationInformation.addProperty("specificationVersion", threeDSAuthenticationResponse.getConsumer_authentication_information().getSpecification_version());
        consumerAuthenticationInformation.addProperty("ucafAuthenticationData", threeDSAuthenticationResponse.getConsumer_authentication_information().getUcaf_authentication_data());
        consumerAuthenticationInformation.addProperty("ucafCollectionIndicator", threeDSAuthenticationResponse.getConsumer_authentication_information().getUcaf_collection_indicator());
        consumerAuthenticationInformation.addProperty("token", threeDSAuthenticationResponse.getConsumer_authentication_information().getToken());
        consumerAuthenticationInformation.addProperty("ucaf", threeDSAuthenticationResponse.getConsumer_authentication_information().getUcaf());
        consumerAuthenticationInformation.addProperty("xid", threeDSAuthenticationResponse.getConsumer_authentication_information().getXid());
        consumerAuthenticationInformation.addProperty("cavv", threeDSAuthenticationResponse.getConsumer_authentication_information().getCavv());
        consumerAuthenticationInformation.addProperty("cavvAlgorithm", threeDSAuthenticationResponse.getConsumer_authentication_information().getCavv_algorithm());
        responseObject.add("consumerAuthenticationInformation", consumerAuthenticationInformation);

        JsonObject card = new JsonObject();
        card.addProperty("numberToken", threeDSAuthenticationResponse.getCard().getNumber_token());
        responseObject.add("card", card);

        responseObject.addProperty("id", threeDSAuthenticationResponse.getId());
        responseObject.addProperty("status", threeDSAuthenticationResponse.getStatus());
        responseObject.addProperty("submitTimeUtc", threeDSAuthenticationResponse.getSubmit_time_utc());

        WSPagtoCartao pagtoCartaoRS = new WSPagtoCartao();
        pagtoCartaoRS.setThreeDS(new WSThreeDS(gson.toJson(responseObject)));
        return new WSPagtoCartaoRS(cartaoRQ.getIntegrador(), pagtoCartaoRS, WSIntegracaoStatusEnum.OK);
    }

    public WSPagtoCartaoRS autorizarThreeDS(WSPagtoCartaoRQ cartaoRQ) throws ErrorException {

        //Autenticacão para obter token que será usada nas demais chamadas
        WSIntegrador integrador = cartaoRQ.getIntegrador();
        autenticar(integrador);
        //[FIM]Autenticacão para obter token que será usada nas demais chamadas

        //Para efetuar transações com segurança, é gerado um token do cartão, o mesmo será utilizado
        CardNumberTokenResponse cartaoTokenRS = gerarTokenCartao(integrador, cartaoRQ.getPagtoCartao());
        //[FIM]Para efetuar transações com segurança, é gerado um token do cartão, o mesmo será utilizado

        //Verifica se o pagamento é feito em 1x somente ou em mais de uma(sem juros)
        String tpTransacao = null;
        if (cartaoRQ.getPagtoCartao().getQtParcela() == 1) {
            tpTransacao = "FULL";
        } else {
            tpTransacao = "INSTALL_NO_INTEREST";
        }
        //[FIM]Verifica se o pagamento é feito em 1x somente ou em mais de uma(sem juros)

        //Verifica se a data de vencimento do cartão é menor que a data atual
        Date dataExpiracao = Utils.toDate(cartaoRQ.getPagtoCartao().getAaValidade() + "-" + cartaoRQ.getPagtoCartao().getMmValidade() + "-01", "yyyy-MM-dd");
        if (dataExpiracao.before(new Date())) {
            throw new ErrorException(integrador, MontaWS.class, "autorizar", WSMensagemErroEnum.GENCONEC, "Cartão Vencido", WSIntegracaoStatusEnum.NEGADO, null);
        }
        //[FIM]Verifica se a data de vencimento do cartão é menor que a data atual

        Card card = UtilsWS.montaCard(cartaoTokenRS.getNumber_token(), cartaoRQ.getPagtoCartao());

        //Valor a ser enviado na api deve ser em centavos, ou seja, sem as vírgulas
        String valor = Utils.formatDouble(cartaoRQ.getPagtoCartao().getVlPagamento(), 2, 2).replace(".", "").replace(",", "");
        int valorPagto = Integer.parseInt(valor);

        //Autorizar uma transação, dados recebidos dados para pagamento com cartão de crédito
        ThreeDSAuthorizationRequest autorizacaoRQ = new ThreeDSAuthorizationRequest(cartaoRQ.getPagtoCartao().getIdTransacao(),
                valorPagto,
                cartaoRQ.getPagtoCartao().getSgMoeda(),
                tpTransacao,
                cartaoRQ.getPagtoCartao().getQtParcela(),
                cartaoRQ.getPagtoCartao().getThreeDS().getXid(),
                cartaoRQ.getPagtoCartao().getThreeDS().getUcaf(),
                cartaoRQ.getPagtoCartao().getThreeDS().getEci(),
                cartaoRQ.getPagtoCartao().getThreeDS().getTdsdsxid(),
                cartaoRQ.getPagtoCartao().getThreeDS().getTdsver(),
                "CREDIT",
                null,//"dynamic_mcc",
                null,
                cartaoRQ.getPagtoCartao().getIdTransacao(),
                "ONE_CLICK",
                card
        );

        ThreeDSAuthorizationResponse autorizacaoRS = getNetClient.authorizeThreeDS(integrador, autorizacaoRQ);
        //[FIM]Autorizar uma transação, dados recebidos dados para pagamento com cartão de crédito

        WSPagtoCartaoStatusEnum statusCartao = null;

        if (autorizacaoRS.getStatus().contains("APPROVED")) {
            statusCartao = WSPagtoCartaoStatusEnum.AUTORIZADO;
        } else {
            statusCartao = WSPagtoCartaoStatusEnum.NEGADO;
        }

        WSPagtoCartao pagtoCartao = new WSPagtoCartao(cartaoRQ.getPagtoCartao().getCartaoTipo(),
                cartaoRQ.getPagtoCartao().getNmTitular(),
                autorizacaoRS.getAcquirer_transaction_id(),//nrAprovacao
                autorizacaoRS.getAuthorization_code(),//nrAutorizacao
                cartaoRQ.getPagtoCartao().getNrCartao(),
                cartaoRQ.getPagtoCartao().getCdSeguranca(),
                cartaoRQ.getPagtoCartao().getMmValidade(),
                cartaoRQ.getPagtoCartao().getAaValidade(),
                cartaoRQ.getPagtoCartao().getVlPagamento(),//vlpagamento
                cartaoRQ.getPagtoCartao().getQtParcela(),
                statusCartao,
                autorizacaoRS.getSeller_id(),//idexterno
                new Date(),//dtTransacao
                autorizacaoRS.getStatus(),//dsmensagem
                cartaoTokenRS.getNumber_token(),//idToken
                autorizacaoRS.getPayment_id()
        );//idtoken

        return new WSPagtoCartaoRS(integrador, pagtoCartao, WSIntegracaoStatusEnum.OK);
    }
}
