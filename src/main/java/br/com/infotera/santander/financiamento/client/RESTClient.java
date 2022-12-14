package br.com.infotera.santander.financiamento.client;

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
import br.com.infotera.santander.financiamento.model.Error;
import br.com.infotera.santander.financiamento.rqrs.AuthTokenRQ;
import br.com.infotera.santander.financiamento.model.FormEncoded;
import br.com.infotera.santander.financiamento.util.ObjectUrlEncodedConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonSyntaxException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpStatusCodeException;

@Service
public class RESTClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;
    
    @Autowired
    private ObjectMapper objectMapper;

    public <T> T sendReceive(WSIntegrador integrador, Object request, HttpMethod httpMethod, String method, Class<T> retorno) throws ErrorException {
        Object result = null;
        ResponseEntity<String> responseEntity = null;
        String endpoint = null;
        String time = Utils.formatData(new Date(), "HHmmss");
        
        WSIntegradorLog log = new WSIntegradorLog(integrador.getDsAction(), WSIntegradorLogTipoEnum.JSON);
        ((HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout((integrador.getTimeoutSegundos() * 1000));

        try {
            // Obtem o EndPoint 
            endpoint = retornarEnvironmentUri(integrador, method);
            
            if(integrador.getSessao() == null){
                // Objeto que monta os dados de autentica????o
                AuthTokenRQ authToken = (AuthTokenRQ) request;
                
                // HttpEntity (Objeto) a ser passado como requisi????o
                // Montado do headers para requisi????es ao fornecedor
                // Montado o Formul??rio (form-encoded)
                // Montado a requisi????o para ser enviada para Autentica????o
                HttpEntity<FormEncoded> entity = new HttpEntity(montarFormUrl(integrador, authToken), montarHeader(integrador));
//                UtilsWS.geraArquivo(gson.toJson(entity), "/home/william/Documentos/", "request_"+integrador.getDsAction()+"_"+time+".json");

                // Injeta o ObjectMapper no RestTemplate para auxilio da convers??o dos par??metros do formul??rio (form-encoded)
                restTemplate.getMessageConverters().add(new ObjectUrlEncodedConverter(objectMapper));
                try {
                    // Retorno do fornecedor referente a chamada a autentica????o (Token)
                    responseEntity = restTemplate.exchange(endpoint, httpMethod, entity, String.class);
                } catch(HttpStatusCodeException e) {
                    responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                          .body(e.getResponseBodyAsString());
                }   
            } else {
                // Objeto a ser passado como requisi????o
                HttpEntity<String> entity = new HttpEntity(gson.toJson(request), montarHeader(integrador));
//                UtilsWS.geraArquivo(gson.toJson(entity), "/home/william/Documentos/", "request_"+integrador.getDsAction()+"_"+time+".json");
                try {
                    // Retorno do fornecedor referente a chamada aos m??todos
                    responseEntity = restTemplate.exchange(endpoint, httpMethod, entity, String.class);
                } catch(HttpStatusCodeException e) {
                    responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                          .body(e.getResponseBodyAsString());
                }
            }
            // LOG - REQUEST
            LogWS.convertRequest(integrador, log, gson, request);
            
            // LOG - RESPONSE
            result = gson.fromJson(responseEntity.getBody(), retorno); //
            LogWS.convertResponse(integrador, log, gson, responseEntity, retorno);
//            UtilsWS.geraArquivo(gson.toJson(result), "/home/william/Documentos/", "response_"+integrador.getDsAction()+"_"+time+".json");
            
            if(result == null) {
                Error error = gson.fromJson((String)responseEntity.getBody(), Error.class);
                verificaErro(integrador, error, method);
            }

        } catch (RestClientException ex) {
            throw LogWS.convertResponseException(integrador, log, ex);
        } catch (ErrorException ex) {
            LogWS.convertResponse(integrador, log, gson, responseEntity, retorno);
            throw ex;
        } catch (JsonSyntaxException ex) {
            integrador.setDsMensagem("Erro " + responseEntity.getStatusCode().toString());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "sendReceive", WSMensagemErroEnum.GENCONVERT, 
                    "Erro ao realizar a chamada ao Fornecedor - Classe de Retorno: " + retorno.getSimpleName() + ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        } finally {
            LogWS.montaLog(integrador, log);
        }
        return (T) result;
    }

    private String montaEncoderBase64(WSIntegrador integrador) throws ErrorException, UnsupportedEncodingException {
        String result = null;
        if (integrador != null) {
            try {
                String client_id = integrador.getDsCredencialList().get(0);
                String client_secret = integrador.getDsCredencialList().get(1);
                String clientCredentials = client_id + ":" + client_secret;
                
                String encoding = "";
                byte array[] = encoding.getBytes("UTF-8"); //  encoding = new String(array, "ISO-8859-1");
                String credentials = String.format(clientCredentials);
                
                byte[] encoded = Base64.getEncoder().encode(credentials.getBytes());
                result = new String(encoded);
            } catch (UnsupportedEncodingException e) {
                throw new ErrorException(integrador, RESTClient.class, "montaEncoderBase64", WSMensagemErroEnum.GENCONVERT, 
                        "Erro ao montar o fator de encripta????o (Base64) " + e.getMessage(), WSIntegracaoStatusEnum.NEGADO, e, false);
            }
        }
        return result;
    }
    
    private HttpHeaders montarHeader(WSIntegrador integrador) throws ErrorException {
        HttpHeaders headers = new HttpHeaders();
        try {
            if(integrador.getSessao() != null) {
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                headers.setBearerAuth(integrador.getSessao().getDsSessao());
                headers.set("Accept-Encoding", "gzip");
                headers.set("Accept", "*/*");
            } else {
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                headers.setBasicAuth(montaEncoderBase64(integrador));
                headers.set("Accept-Encoding", "gzip");
            } 
        } catch (ErrorException | UnsupportedEncodingException ex) {
            throw new ErrorException(integrador, RESTClient.class, "montarHeader", WSMensagemErroEnum.GENMETHOD, 
                    "Erro ao obter Token para Autentica????o " + ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return headers;
    }

    public String retornarEnvironmentUri(WSIntegrador integrador, String method) throws ErrorException {
        String endpoint = null;
        String[] methods = null;
        
        try {
            methods = method.contains("/") ? method.split("\\/") : new String[1];
            if(methods.length < 2){
                methods[0] = method;
            }
        } catch (Exception e) {
            throw new ErrorException(integrador, RESTClient.class, "retornarEnvironmentUri", WSMensagemErroEnum.GENENDPOINT, 
                    "Erro ao obter o m??todo para chamada de endpoint " + e.getMessage(), WSIntegracaoStatusEnum.NEGADO, e, false);
        }
        
        try {
            if (integrador.getAmbiente().equals(WSAmbienteEnum.PRODUCAO)) {
                endpoint = "https://brpiosantanderapi.viverebrasil.com.br" + "/" + method;
            } else if (integrador.getAmbiente().equals(WSAmbienteEnum.HOMOLOGACAO)) {
                switch (methods[0]) {
                    case "token":
                        endpoint = "https://brpiosantanderapihml.viverebrasil.com.br" + "/" + methods[0];
                        break;
                    case "products":
                    case "register":
                        endpoint = "https://brpiosantanderapihml.viverebrasil.com.br/domains"+ "/" +  methods[0] + "/" + methods[1];
                        break;    
                    case "list-terms":
                    case "consent-register":
                    case "ckeck-list":
                        endpoint = "https://brpiosantanderapihml.viverebrasil.com.br/domains"+ "/" +  methods[0];
                        break;
                    case "simulation":
                    case "finish":
                        endpoint = (methods.length > 1) ? methods[1] : methods[0];
                        endpoint = "https://brpiosantanderapihml.viverebrasil.com.br/simulation" + "/" + endpoint;
                        break;   
                    case "save":
                        endpoint = "https://brpiosantanderapihml.viverebrasil.com.br/proposal"+ "/" +  methods[0];
                        break;
                    default:
                        throw new AssertionError();
                }
            }
        } catch (Exception e) {
            throw new ErrorException(integrador, RESTClient.class, "retornarEnvironmentUri", WSMensagemErroEnum.GENENDPOINT, 
                    "Erro ao obter o Endpoint para realizar a chamada ao Fornecedor " + e.getMessage(), WSIntegracaoStatusEnum.NEGADO, e, true);
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
            
            dsStatus = response.getStatusCode() != null ? response.getStatusCode() : response.getFault() != null && !response.getFault().getMessage().equals("") ? response.getFault().getMessage() : "";
            dsMsg = response.getMessages() != null && !response.getMessages().isEmpty() ? response.getMessages().get(0).getMessage() : response.getFault() != null ? response.getFault().getDescription() : "";
            
            switch (response.getStatusCode()) {
                case "400":
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENENDPOINT, 
                            "Erro do conector: CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "401":
                    //Erro generico, mensagem especificada na documenta????o
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENENDPOINT, 
                            "Erro do conector: O usu??rio e senha ou token de acesso s??o inv??lidos! CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "403":
                    //Erro generico, mensagem especificada na documenta????o
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENENDPOINT, 
                            "Erro do conector: O acesso ?? API est?? bloqueado ou o usu??rio est?? bloqueado! CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "404":
                    //Erro generico, mensagem especificada na documenta????o
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENENDPOINT, 
                            "Erro do conector: O endere??o acessado n??o existe! CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "405":
                    //Erro generico, mensagem especificada na documenta????o
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENENDPOINT, 
                            "Erro do conector: O acesso ao m??todo n??o permitido! CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "406":
                    //Erro generico, mensagem especificada na documenta????o
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENENDPOINT, 
                            "Erro do conector: A requisi????o est?? fora do formato (JSON) permitido! CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "422":
                    //Erro 422 n??o retorna lista de campos, apenas mensagem
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENENDPOINT, 
                            "Erro do conector: CODERROR - " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "429":
                    //Erro generico, mensagem especificada na documenta????o
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENENDPOINT, 
                            "Erro do conector: O usu??rio atingiu o limite de requisi????es! CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "500":
                    //Erro generico, mensagem especificada na documenta????o
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENENDPOINT, 
                            "Erro do conector: Reportar o erro para o time tecnico! CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "503":
                case "504":
                    //Erro generico, mensagem especificada na documenta????o
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENENDPOINT, 
                            "Erro do conector: Servidor temporariamente off-line! Tentar novamente mais tarde - CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                default:
            }
        }
    }

    private FormEncoded montarFormUrl(WSIntegrador integrador, AuthTokenRQ authToken) throws ErrorException{
        FormEncoded form = new FormEncoded();
        try {
            form.setGrantType("password");
            form.setUsername(authToken.getUsername());
            form.setPassword(authToken.getPassword());
            form.setBusinessCode(2);
            form.setLoginTypeId(9);
            form.setTpLoginCode("00008");
            form.setStoreId(authToken.getStoreId() != null && Utils.isNumerico(authToken.getStoreId()) ? authToken.getStoreId() : "null");
            form.setRevokeSession(Boolean.TRUE);
        } catch (NumberFormatException e) {
            throw new ErrorException(integrador, RESTClient.class, "montarFormUrl", WSMensagemErroEnum.GENVAL, 
                    "Erro ao montar formul??rio (FormEncoded)" + e.getMessage(), WSIntegracaoStatusEnum.NEGADO, e, false);
        }
        return form;
    }
}
