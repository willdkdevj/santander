package  br.com.infotera.santander.getnet.client;

import br.com.infotera.common.ErrorException;
import br.com.infotera.common.WSIntegrador;
import br.com.infotera.common.WSSessao;
import br.com.infotera.common.enumerator.WSAmbienteEnum;
import br.com.infotera.common.enumerator.WSIntegracaoStatusEnum;
import br.com.infotera.common.enumerator.WSMensagemErroEnum;
import br.com.infotera.common.util.Utils;
import br.com.infotera.santander.getnet.model.AuthenticateRequest;
import br.com.infotera.santander.getnet.model.AuthenticateResponse;
import br.com.infotera.santander.getnet.model.AuthorizationRequest;
import br.com.infotera.santander.getnet.model.AuthorizationResponse;
import br.com.infotera.santander.getnet.model.CancelRequest;
import br.com.infotera.santander.getnet.model.CancelResponse;
import br.com.infotera.santander.getnet.model.CaptureRequest;
import br.com.infotera.santander.getnet.model.CaptureResponse;
import br.com.infotera.santander.getnet.model.CardNumberTokenRequest;
import br.com.infotera.santander.getnet.model.CardNumberTokenResponse;
import br.com.infotera.santander.getnet.model.ThreeDSAuthenticationRequest;
import br.com.infotera.santander.getnet.model.ThreeDSAuthenticationResponse;
import br.com.infotera.santander.getnet.model.ThreeDSAuthenticationResultRequest;
import br.com.infotera.santander.getnet.model.ThreeDSAuthenticationResultResponse;
import br.com.infotera.santander.getnet.model.ThreeDSAuthorizationRequest;
import br.com.infotera.santander.getnet.model.ThreeDSAuthorizationResponse;
import br.com.infotera.santander.getnet.model.ThreeDsTokenRequest;
import br.com.infotera.santander.getnet.model.ThreeDsTokenResponse;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class GetNetClient {

    @Autowired
    private RESTClient restClient;

    public WSSessao authenticate(WSIntegrador integrador, AuthenticateRequest request) throws ErrorException {

        String dsMetodo = "auth/oauth/v2/token";
        String endpoint = montaEnvironmentUri(integrador, dsMetodo);

        WSSessao result;
        integrador.setDsAction("autenticacao");

        try {
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("scope", request.getScope());
            requestBody.add("grant_type", request.getGrant_type());

            AuthenticateResponse authenticateResponse = restClient.sendReceive(integrador, requestBody, HttpMethod.POST, endpoint, AuthenticateResponse.class);
            result = new WSSessao(authenticateResponse.getAccess_token(), new Date(), Utils.addMilisegundos(new Date(), authenticateResponse.getExpires_in()));
        } catch (ErrorException ex) {
            if (integrador.getDsMensagem() == null) {
                integrador.setDsMensagem(ex.getMessage());
            }
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, GetNetClient.class, "Erro a o chamar authenticate", WSMensagemErroEnum.AAU, ex.getMessage(), WSIntegracaoStatusEnum.OK, ex);
        }

        return result;
    }

    public CardNumberTokenResponse generateCardNumberToken(WSIntegrador integrador, CardNumberTokenRequest request) throws ErrorException {

        String dsMetodo = "v1/tokens/card";
        String endpoint = montaEnvironmentUri(integrador, dsMetodo);

        CardNumberTokenResponse result;
        integrador.setDsAction("tokenizacao");

        try {
            result = restClient.sendReceive(integrador, request, HttpMethod.POST, endpoint, CardNumberTokenResponse.class);
        } catch (ErrorException ex) {
            if (integrador.getDsMensagem() == null) {
                integrador.setDsMensagem(ex.getMessage());
            }
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, GetNetClient.class, "Erro ao enviar generateCardNumberToken", WSMensagemErroEnum.GENMETHOD, ex.getMessage(), WSIntegracaoStatusEnum.OK, ex);
        }

        return result;
    }

    public AuthorizationResponse authorize(WSIntegrador integrador, AuthorizationRequest request) throws ErrorException {

        String dsMetodo = "v1/payments/credit";
        String endpoint = montaEnvironmentUri(integrador, dsMetodo);

        AuthorizationResponse result;
        integrador.setDsAction("autorizacao");

        try {
            result = restClient.sendReceive(integrador, request, HttpMethod.POST, endpoint, AuthorizationResponse.class);
        } catch (ErrorException ex) {
            if (integrador.getDsMensagem() == null) {
                integrador.setDsMensagem(ex.getMessage());
            }
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, GetNetClient.class, "Erro ao enviar authorization", WSMensagemErroEnum.GENMETHOD, ex.getMessage(), WSIntegracaoStatusEnum.OK, ex);
        }

        return result;
    }

    public CaptureResponse captura(WSIntegrador integrador, CaptureRequest request) throws ErrorException {

        String dsMetodo = "v1/payments/credit/" + request.getPayment_id() + "/confirm";
        String endpoint = montaEnvironmentUri(integrador, dsMetodo);

        CaptureResponse result;
        integrador.setDsAction("captura");

        try {
            result = restClient.sendReceive(integrador, request.getAmount(), HttpMethod.POST, endpoint, CaptureResponse.class);
        } catch (ErrorException ex) {
            if (integrador.getDsMensagem() == null) {
                integrador.setDsMensagem(ex.getMessage());
            }
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, GetNetClient.class, "Erro ao enviar captura", WSMensagemErroEnum.GENMETHOD, ex.getMessage(), WSIntegracaoStatusEnum.OK, ex);
        }

        return result;
    }

    public CancelResponse cancelar(WSIntegrador integrador, CancelRequest request) throws ErrorException {
        Object requestObject = request.getCancel();

        String dsMetodo = "v1/payments/credit/" + request.getCancel().getPayment_id() + "/cancel";
        if (request.isOutroDia()) {
            dsMetodo = "v1/payments/cancel/request";
            requestObject = "";
        }
        String endpoint = montaEnvironmentUri(integrador, dsMetodo);

        CancelResponse result;
        integrador.setDsAction("cancelar");

        try {
            result = restClient.sendReceive(integrador, requestObject, HttpMethod.POST, endpoint, CancelResponse.class);
        } catch (ErrorException ex) {
            if (integrador.getDsMensagem() == null) {
                integrador.setDsMensagem(ex.getMessage());
            }
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, GetNetClient.class, "Erro ao enviar cancelar", WSMensagemErroEnum.GENMETHOD, ex.getMessage(), WSIntegracaoStatusEnum.OK, ex);
        }

        return result;
    }

    public ThreeDsTokenResponse generateTokenThreeDS(WSIntegrador integrador, ThreeDsTokenRequest request) throws ErrorException {

        String dsMetodo = "v1/3ds/tokens";
        String endpoint = montaEnvironmentUri(integrador, dsMetodo);

        ThreeDsTokenResponse result;
        integrador.setDsAction("tokenThreeDS");

        try {
            result = restClient.sendReceive(integrador, request, HttpMethod.POST, endpoint, ThreeDsTokenResponse.class);
        } catch (ErrorException ex) {
            if (integrador.getDsMensagem() == null) {
                integrador.setDsMensagem(ex.getMessage());
            }
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, GetNetClient.class, "Erro a o chamar generateTokenThreeDS", WSMensagemErroEnum.AAU, ex.getMessage(), WSIntegracaoStatusEnum.OK, ex);
        }

        return result;
    }

    public ThreeDSAuthenticationResponse authenticateThreeDS(WSIntegrador integrador, ThreeDSAuthenticationRequest request) throws ErrorException {

        String dsMetodo = "v1/3ds/authentications";
        String endpoint = montaEnvironmentUri(integrador, dsMetodo);

        ThreeDSAuthenticationResponse result;
        integrador.setDsAction("autenticacaoThreeDS");

        try {
            result = restClient.sendReceive(integrador, request, HttpMethod.POST, endpoint, ThreeDSAuthenticationResponse.class);
        } catch (ErrorException ex) {
            if (integrador.getDsMensagem() == null) {
                integrador.setDsMensagem(ex.getMessage());
            }
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, GetNetClient.class, "Erro a o chamar authenticateThreeDS", WSMensagemErroEnum.AAU, ex.getMessage(), WSIntegracaoStatusEnum.OK, ex);
        }

        return result;
    }

    public ThreeDSAuthenticationResultResponse authenticationResultThreeDS(WSIntegrador integrador, ThreeDSAuthenticationResultRequest request) throws ErrorException {

        String dsMetodo = "v1/3ds/authentications/results";
        String endpoint = montaEnvironmentUri(integrador, dsMetodo);

        ThreeDSAuthenticationResultResponse result;
        integrador.setDsAction("authenticationResultThreeDS");

        try {
            result = restClient.sendReceive(integrador, request, HttpMethod.POST, endpoint, ThreeDSAuthenticationResultResponse.class);
        } catch (ErrorException ex) {
            if (integrador.getDsMensagem() == null) {
                integrador.setDsMensagem(ex.getMessage());
            }
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, GetNetClient.class, "Erro a o chamar authenticationResultThreeDS", WSMensagemErroEnum.AAU, ex.getMessage(), WSIntegracaoStatusEnum.OK, ex);
        }

        return result;
    }

    public CaptureResponse capturaThreeDS(WSIntegrador integrador, CaptureRequest request) throws ErrorException {

        String dsMetodo = "v1/payments/authenticated/" + request.getPayment_id() + "/confirm";
        String endpoint = montaEnvironmentUri(integrador, dsMetodo);

        CaptureResponse result;
        integrador.setDsAction("capturaThreeDS");

        try {
            result = restClient.sendReceive(integrador, request.getAmount(), HttpMethod.POST, endpoint, CaptureResponse.class);
        } catch (ErrorException ex) {
            if (integrador.getDsMensagem() == null) {
                integrador.setDsMensagem(ex.getMessage());
            }
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, GetNetClient.class, "Erro ao enviar capturaThreeDS", WSMensagemErroEnum.GENMETHOD, ex.getMessage(), WSIntegracaoStatusEnum.OK, ex);
        }

        return result;
    }

    public CancelResponse cancelarThreeDS(WSIntegrador integrador, CancelRequest request) throws ErrorException {
        String dsMetodo = "v1/payments/authenticated/" + request.getPayment_id() + "/cancel";

        String endpoint = montaEnvironmentUri(integrador, dsMetodo);

        CancelResponse result;
        integrador.setDsAction("cancelarThreeDS");

        try {
            result = restClient.sendReceive(integrador, request.getCancel(), HttpMethod.POST, endpoint, CancelResponse.class);
        } catch (ErrorException ex) {
            if (integrador.getDsMensagem() == null) {
                integrador.setDsMensagem(ex.getMessage());
            }
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, GetNetClient.class, "Erro ao enviar cancelarThreeDS", WSMensagemErroEnum.GENMETHOD, ex.getMessage(), WSIntegracaoStatusEnum.OK, ex);
        }

        return result;
    }

    public ThreeDSAuthorizationResponse authorizeThreeDS(WSIntegrador integrador, ThreeDSAuthorizationRequest request) throws ErrorException {

        String dsMetodo = "v1/payments/authenticated";
        String endpoint = montaEnvironmentUri(integrador, dsMetodo);

        ThreeDSAuthorizationResponse result;
        integrador.setDsAction("authorizeThreeDS");

        try {
            result = restClient.sendReceive(integrador, request, HttpMethod.POST, endpoint, ThreeDSAuthorizationResponse.class);
        } catch (ErrorException ex) {
            if (integrador.getDsMensagem() == null) {
                integrador.setDsMensagem(ex.getMessage());
            }
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, GetNetClient.class, "Erro ao enviar authorizeThreeDS", WSMensagemErroEnum.GENMETHOD, ex.getMessage(), WSIntegracaoStatusEnum.OK, ex);
        }

        return result;
    }

    private String montaEnvironmentUri(WSIntegrador integrador, String method) throws ErrorException {
        String result = "";
        try {

            if (integrador.getAmbiente().equals(WSAmbienteEnum.PRODUCAO)) {
                result = "https://api.getnet.com.br/" + method;
            } else {
                result = "https://api-homologacao.getnet.com.br/" + method;
            }
        } catch (Exception e) {
            throw new ErrorException(integrador, RESTClient.class, "Erro ao obter o Endpoint", WSMensagemErroEnum.PGP, e.getMessage(), WSIntegracaoStatusEnum.NEGADO, e, true);
        }

        return result;
    }
}
