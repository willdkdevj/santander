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
import br.com.infotera.santander.model.FormEncoded;
import br.com.infotera.santander.util.ObjectUrlEncodedConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
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

        WSIntegradorLog log = new WSIntegradorLog(integrador.getDsAction(), WSIntegradorLogTipoEnum.JSON);
        ((HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout((integrador.getTimeoutSegundos() * 1000));

        try {
            // Obtem o EndPoint 
            endpoint = retornarEnvironmentUri(integrador, method);
            
            if(method.equals("auth")){
                AuthTokenRQ authToken = (AuthTokenRQ) request;
                // Objeto a ser passado como requisição
                HttpEntity<FormEncoded> entity = null;
                
                // Retorno do headers para requisições ao fornecedor
                HttpHeaders headers = montarHeader(integrador, method);
                
                // Montado o Formulário (form-encoded)
                FormEncoded formUrl = montarFormUrl(integrador, authToken);
                
                // Montado a requisição para envido para Autenticação
                entity = new HttpEntity(formUrl, headers);
//                UtilsWS.geraArquivo(gson.toJson(entity), "/home/william/Documentos/", "requestToken.json");

                // Injeta o ObjectMapper no RestTemplate para auxilio da conversão dos parâmetros do formulário (form-encoded)
                restTemplate.getMessageConverters().add(new ObjectUrlEncodedConverter(objectMapper));
                
                try {
                    // Retorno do fornecedor referente a chamada a autenticação (Token)
                    responseEntity = restTemplate.exchange(endpoint, httpMethod, entity, String.class); //restTemplate.postForObject(endpoint, entity, String.class);
                } catch(HttpStatusCodeException e) {
                    responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                          .body(e.getResponseBodyAsString());
                }   
            } else {
                // Objeto a ser passado como requisição
                HttpEntity<String> entity = new HttpEntity(gson.toJson(request), montarHeader(integrador, method));
                try {
                    // Retorno do fornecedor referente a chamada aos métodos
                    responseEntity = restTemplate.exchange(endpoint, httpMethod, entity, String.class);
                } catch(HttpStatusCodeException e) {
                    responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                          .body(e.getResponseBodyAsString());
                }
            }
            // LOG - REQUEST
            LogWS.convertRequest(integrador, log, gson, request);
//            LogWS.convertRequest(integrador, log, objectMapper, request);
            
            // LOG - RESPONSE
            result = LogWS.convertResponse(integrador, log, gson, responseEntity, retorno); // gson.fromJson(responseEntity.getBody(), retorno); //
//            result = LogWS.convertResponse(integrador, log, objectMapper, responseEntity, retorno);
//            UtilsWS.geraArquivo(gson.toJson(result), "/home/william/Documentos/", "responseToken.json");
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
                        "Erro ao montar o fator de encriptação (Base64)" + e.getMessage(), WSIntegracaoStatusEnum.NEGADO, e, false);
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
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                headers.setBasicAuth(montaEncoderBase64(integrador));
                headers.set("Accept-Encoding", "gzip");
            } else {
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                headers.setBearerAuth(tokenBearer);
                headers.set("Accept-Encoding", "gzip");
                headers.set("Accept", "*/*");
            }
            
        } catch (Exception ex) {
            throw new ErrorException(integrador, RESTClient.class, "montarHeader", WSMensagemErroEnum.GENMETHOD, 
                    "Erro ao obter Token para Autenticação" + ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return headers;
    }

    public String retornarEnvironmentUri(WSIntegrador integrador, String method) throws ErrorException {
        String endpoint = null;
        String[] methods = null;
        
        try {
            methods = method.contains("\\/") ? method.split("\\/") : new String[1];
            if(methods.length < 2){
                methods[0] = method;
            }
        } catch (Exception e) {
            throw new ErrorException(integrador, RESTClient.class, "retornarEnvironmentUri", WSMensagemErroEnum.GENENDPOINT, 
                    "Erro ao obter o método para chamada de endpoint" + e.getMessage(), WSIntegracaoStatusEnum.NEGADO, e, false);
        }
        
        try {
            if (integrador.getAmbiente().equals(WSAmbienteEnum.PRODUCAO)) {
                endpoint = "https://brpiosantanderapi.viverebrasil.com.br" + "/" + method;
            } else {
                switch (methods[0]) {
                    case "auth":
                        endpoint = "https://brpiosantanderapihml.viverebrasil.com.br" + "/" + "token";
                        break;
                    case "products":
                        endpoint = "https://brpiosantanderapihml.viverebrasil.com.br/domains"+ "/" +  methods[0] + "/" + methods[1];
                        break;    
                    case "list-terms":
                    case "consent-register":
                        endpoint = "https://brpiosantanderapihml.viverebrasil.com.br/domains"+ "/" +  methods[0];
                        break;
                    case "simulation":
                        endpoint = "https://brpiosantanderapihml.viverebrasil.com.br/simulation"+ "/" +  methods[0];
                        break;   
                    default:
                        throw new AssertionError();
                }
                
            }

        } catch (Exception e) {
            throw new ErrorException(integrador, RESTClient.class, "retornarEnvironmentUri", WSMensagemErroEnum.GENENDPOINT, 
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
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENENDPOINT, 
                            "Erro do conector: CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "401":
                    //Erro generico, mensagem especificada na documentação
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENENDPOINT, 
                            "Erro do conector: O usuário e senha ou token de acesso são inválidos! CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "403":
                    //Erro generico, mensagem especificada na documentação
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENENDPOINT, 
                            "Erro do conector: O acesso à API está bloqueado ou o usuário está bloqueado! CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "404":
                    //Erro generico, mensagem especificada na documentação
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENENDPOINT, 
                            "Erro do conector: O endereço acessado não existe! CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "405":
                    //Erro generico, mensagem especificada na documentação
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENENDPOINT, 
                            "Erro do conector: O acesso ao método não permitido! CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "406":
                    //Erro generico, mensagem especificada na documentação
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENENDPOINT, 
                            "Erro do conector: A requisição está fora do formato (JSON) permitido! CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "422":
                    //Erro 422 não retorna lista de campos, apenas mensagem
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENENDPOINT, 
                            "Erro do conector: CODERROR - " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "429":
                    //Erro generico, mensagem especificada na documentação
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENENDPOINT, 
                            "Erro do conector: O usuário atingiu o limite de requisições! CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
                case "503":
                    //Erro generico, mensagem especificada na documentação
                    throw new ErrorException(integrador, RESTClient.class, "verificarErro", WSMensagemErroEnum.GENENDPOINT, 
                            "Erro do conector: Servidor temporariamente off-line! CODERROR: " + metodo + " " + dsStatus + " " + dsMsg, WSIntegracaoStatusEnum.NEGADO, null, false);
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
            form.setTpLoginCode(8);
            form.setStoreId(authToken.getStoreId() != null && Utils.isNumerico(authToken.getStoreId()) ? Integer.parseInt(authToken.getStoreId()) : null);
            form.setRevokeSession(Boolean.TRUE);
        } catch (NumberFormatException e) {
            throw new ErrorException(integrador, RESTClient.class, "montarFormUrl", WSMensagemErroEnum.GENVAL, 
                    "Erro ao montar formulário (FormEncoded)" + e.getMessage(), WSIntegracaoStatusEnum.NEGADO, e, false);
        }
        return form;
    }
}
