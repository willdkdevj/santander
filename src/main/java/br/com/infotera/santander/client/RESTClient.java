package br.com.infotera.santander.client;

import br.com.infotera.common.ErrorException;
import br.com.infotera.common.WSIntegrador;
import br.com.infotera.common.WSIntegradorLog;
import br.com.infotera.common.enumerator.WSAmbienteEnum;
import br.com.infotera.common.enumerator.WSIntegracaoStatusEnum;
import br.com.infotera.common.enumerator.WSIntegradorLogTipoEnum;
import br.com.infotera.common.enumerator.WSMensagemErroEnum;
import br.com.infotera.common.util.LogWS;
import br.com.infotera.common.util.Utils;
import com.google.gson.Gson;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import br.com.infotera.santander.model.Error;
import br.com.infotera.santander.model.RQRS.AuthTokenRQ;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class RESTClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;

    public <T> T sendReceive(WSIntegrador integrador, Object request, HttpMethod httpMethod, String method, Class<T> retorno) throws ErrorException {
        Object result = null;
        ResponseEntity<String> responseEntity = null;
        String endpoint = null;

        WSIntegradorLog log = new WSIntegradorLog(integrador.getDsAction(), WSIntegradorLogTipoEnum.JSON);
        ((HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout((integrador.getTimeoutSegundos() * 1000));

        try {
//            RequestEntity reqEnt = new RequestEntity(result, headers, httpMethod, url);
            HttpEntity<String> entity = null;
            if(method.equals("auth")){
                AuthTokenRQ authToken = (AuthTokenRQ) request;
                MultiValueMap<String, String> dataForm = montarMultiValue(integrador, authToken); //new LinkedMultiValueMap<>();
//                dataForm.add("grant_type", "client_credentials");
                entity = new HttpEntity(dataForm, montarHeader(integrador, method));
                
//                entity = new HttpEntity("grant_type=client_credentials", montaHeaderAutenticacaoRQ(integrador));
            } else {
                entity = new HttpEntity(gson.toJson(request), montarHeader(integrador, method));
            }
            LogWS.convertRequest(integrador, log, gson, request);

            endpoint = retornarEnvironmentUri(integrador, method);
            responseEntity = restTemplate.exchange(endpoint, httpMethod, entity, String.class);

            result = LogWS.convertResponse(integrador, log, gson, responseEntity, retorno);

            if(result == null) {
                Error error = gson.fromJson((String)responseEntity.getBody(), Error.class);
                verificaErro(integrador, error, method);
            }

        } catch (RestClientException ex) {
            throw LogWS.convertResponseException(integrador, log, ex);
        } catch (ErrorException ex) {
            LogWS.convertResponse(integrador, log, gson, responseEntity, retorno);
            throw ex;
        } catch (Exception ex) {
            integrador.setDsMensagem("Erro " + responseEntity.getStatusCode().toString());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "Erro ao realizar a chamada ao Fornecedor - Classe de Retorno: " + retorno.getSimpleName(), WSMensagemErroEnum.PGP, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        } finally {
            LogWS.montaLog(integrador, log);
        }
        return (T) result;
    }

//    private HttpHeaders montaHeaderAutenticacaoRQ(WSIntegrador integrador) throws ErrorException {
//        HttpHeaders result = null;
//        if (integrador != null) {
//            result = new HttpHeaders();
//            try {
//                result.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//                result.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//                result.setBasicAuth(montaEncoderBase64(integrador));
//                result.set("Accept-Encoding", "gzip");
//                result.set("Accept", "*/*");
//                result.set("Authorization", "Basic " + montaEncoderBase64(integrador));
//            } catch (Exception ex) {
//                throw new ErrorException(integrador, RESTClient.class, "Erro ao montar o cabeçalho com a autenticação", WSMensagemErroEnum.PGP, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex);
//            }
//        }
//        return result;
//    }

    private String montaEncoderBase64(WSIntegrador integrador) throws ErrorException, UnsupportedEncodingException {
        String result = null;
        if (integrador != null) {
            try {
                String client_id = integrador.getDsCredencialList().get(0);
                String client_secret = integrador.getDsCredencialList().get(1);
                String clientCredentials = client_id + ":" + client_secret;
                String encoding = "";
                byte array[] = encoding.getBytes("UTF-8");
                //  encoding = new String(array, "ISO-8859-1");
                String credentials = String.format(clientCredentials);
                byte[] encoded = Base64.getEncoder().encode(credentials.getBytes());
                result = new String(encoded);
            } catch (UnsupportedEncodingException e) {
                throw new ErrorException(integrador, RESTClient.class, "Erro ao montar o fator de encriptação (Base64)", WSMensagemErroEnum.PGP, e.getMessage(), WSIntegracaoStatusEnum.NEGADO, e);
            }
        }
        return result;
    }

    private HttpHeaders montarHeader(WSIntegrador integrador, String metodo) throws ErrorException {
        HttpHeaders headers = new HttpHeaders();
        try {
            String tokenBearer = null;

            if(integrador.getSessao() != null) {
                tokenBearer = integrador.getSessao().getDsSessao();
            }

            if (metodo.equals("auth")) {
                headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.setBasicAuth(montaEncoderBase64(integrador));
                headers.set("Accept-Encoding", "gzip");
                headers.set("Accept", "*/*");
                
//                headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//                headers.set("Content-Type", "application/json");
//                headers.set("Accept", "application/json");
//                headers.set("Accept-Encoding", "gzip");
//                headers.set("Accept", "*/*");
//                headers.set("Authorization", "Basic " + tokenBasic);
            } else {
                headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                headers.set("Content-Type", "application/json");
                headers.set("Accept-Encoding", "gzip");
                headers.set("Accept", "*/*");
                headers.set("Authorization", "Bearer " + tokenBearer);
            }
            
        } catch (Exception ex) {
            throw new ErrorException(integrador, RESTClient.class, "Erro ao obter Token para Autenticação", WSMensagemErroEnum.PGP, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return headers;
    }

    public String retornarEnvironmentUri(WSIntegrador integrador, String method) throws ErrorException {
        String endpoint = null;
        String[] methods = null;
        
        try {
            methods = method.contains("\\/") ? method.split("\\/") : new String[0];
            if(methods.length < 2){
                methods[0] = method;
            }
        } catch (Exception e) {
            throw new ErrorException(integrador, RESTClient.class, "retornarEnvironmentUri", WSMensagemErroEnum.PGP, 
                    "Erro ao obter o método para chamada de endpoint" + e.getMessage(), WSIntegracaoStatusEnum.NEGADO, e, false);
        }
        
        try {
            if (integrador.getAmbiente().equals(WSAmbienteEnum.PRODUCAO)) {
                endpoint = "https://core.usevirtus.com.br/api" + "/" + method;
            } else {
                switch (methods[0]) {
                    case "auth":
                        endpoint = "https://brpiosantanderapihml.viverebrasil.com.br" + "/" + "token";
                        break;
                    default:
                        throw new AssertionError();
                }
                
            }

        } catch (Exception e) {
            throw new ErrorException(integrador, RESTClient.class, "retornarEnvironmentUri", WSMensagemErroEnum.PGP, 
                    "Erro ao obter o Endpoint para realizar a chamada ao Fornecedor" + e.getMessage(), WSIntegracaoStatusEnum.NEGADO, e, true);
        }

        return endpoint;
    }

    private void verificaErro(WSIntegrador integrador, Error response, String metodo) throws ErrorException {
        String dsStatus = null;
        String dsMsg = null;
        
        if(metodo.contains("/")){
            String[] divUrl = metodo.split("\\/");
            metodo = divUrl[0];
        }

        if(response != null) {
            dsStatus = response.getStatusCode();
            
            switch (response.getStatusCode()) {
                case "400":
                case "500":
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENMETHOD, "Erro do conector: CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "401":
                    //Erro generico, mensagem especificada na documentação
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENMETHOD, "Erro do conector: O usuário e senha ou token de acesso são inválidos! CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "403":
                    //Erro generico, mensagem especificada na documentação
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENMETHOD, "Erro do conector: O acesso à API está bloqueado ou o usuário está bloqueado! CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "404":
                    //Erro generico, mensagem especificada na documentação
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENMETHOD, "Erro do conector: O endereço acessado não existe! CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "405":
                    //Erro generico, mensagem especificada na documentação
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENMETHOD, "Erro do conector: O acesso ao método não permitido! CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "406":
                    //Erro generico, mensagem especificada na documentação
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENMETHOD, "Erro do conector: A requisição está fora do formato (JSON) permitido! CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "422":
                    //Erro 422 não retorna lista de campos, apenas mensagem
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENMETHOD, "Erro do conector: CODERROR - " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "429":
                    //Erro generico, mensagem especificada na documentação
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENMETHOD, "Erro do conector: O usuário atingiu o limite de requisições! CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "503":
                    //Erro generico, mensagem especificada na documentação
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENMETHOD, "Erro do conector: Servidor temporariamente off-line! CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                default:
                    
            }
        }

    }

    private MultiValueMap<String, String> montarMultiValue(WSIntegrador integrador, AuthTokenRQ authToken) throws ErrorException {
        MultiValueMap<String, String> dataForm = null;
        try {
            if(!Utils.isListNothing(integrador.getDsCredencialList())){
                dataForm = new LinkedMultiValueMap<>();
                dataForm.add("grant_type", "password");
                dataForm.add("username", authToken.getUsername());
                dataForm.add("password", authToken.getPassword());
                dataForm.add("businessCode", "2");
                dataForm.add("loginTypeId", "9");
                dataForm.add("tpLoginCode", "00008");
                dataForm.add("storeId", authToken.getStoreId());
                dataForm.add("revokeSession", "true");
            } else {
                 throw new ErrorException(integrador, RESTClient.class, "montarMultiValue", 
                         WSMensagemErroEnum.GENVAL, "Erro ao obter as credenciais para validação. Entre em contato com o suporte!", WSIntegracaoStatusEnum.NEGADO, null, true);
            }
        } catch (Exception e) {
            throw new ErrorException(integrador, RESTClient.class, "montarMultiValue", 
                    WSMensagemErroEnum.GENVAL, "Erro ao montar formulário (MultiValueMap)" + e.getMessage(), WSIntegracaoStatusEnum.NEGADO, e, true);
        }
        
        return dataForm;
    }
}
