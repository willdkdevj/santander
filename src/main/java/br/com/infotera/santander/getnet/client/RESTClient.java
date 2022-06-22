package br.com.infotera.santander.getnet.client;

import br.com.infotera.common.ErrorException;
import br.com.infotera.common.WSIntegrador;
import br.com.infotera.common.WSIntegradorLog;
import br.com.infotera.common.enumerator.WSIntegracaoStatusEnum;
import br.com.infotera.common.enumerator.WSIntegradorLogTipoEnum;
import br.com.infotera.common.enumerator.WSMensagemErroEnum;
import br.com.infotera.common.util.LogWS;
import br.com.infotera.santander.getnet.model.sdk.Details;
import br.com.infotera.santander.getnet.model.sdk.ErrorBase;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Base64;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;

@Service(value = "RESTClientGetNet")
public class RESTClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MappingJackson2HttpMessageConverter converter;

    @Autowired
    private Gson gson;

    public <T> T sendReceive(WSIntegrador integrador, Object request, HttpMethod httpMethod, String method, Class<T> retorno) throws ErrorException {
        Object result = null;
        ResponseEntity<String> responseEntity = null;
        WSIntegradorLog log = new WSIntegradorLog(integrador.getDsAction(), WSIntegradorLogTipoEnum.JSON);
        ((HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout((integrador.getTimeoutSegundos() * 1000));

        try {
            restTemplate.getMessageConverters().add(converter);
            HttpEntity<String> entity;

            if (request instanceof MultiValueMap) {
                MultiValueMap<String, String> requestBody = (MultiValueMap<String, String>) request;
                entity = new HttpEntity(requestBody, montaHeader(integrador, MediaType.APPLICATION_FORM_URLENCODED));
            } else {
                if (httpMethod.equals(HttpMethod.GET)) {
                    entity = new HttpEntity(montaHeader(integrador, MediaType.APPLICATION_JSON));
                } else {
                    String requestGson = gson.toJson(request);
                    entity = new HttpEntity(requestGson, montaHeader(integrador, MediaType.APPLICATION_JSON));
                }
            }

            LogWS.convertRequest(integrador, log, gson, request);
            responseEntity = restTemplate.exchange(method, httpMethod, entity, String.class);
            result = LogWS.convertResponse(integrador, log, gson, responseEntity, retorno);

            verificaErro(integrador, responseEntity);
        } catch (RestClientException ex) {
            throw LogWS.convertResponseException(integrador, log, ex);
        } catch (ErrorException ex) {
            LogWS.convertResponse(integrador, log, gson, responseEntity, retorno);
            throw ex;
        } catch (Exception ex) {
            integrador.setDsMensagem("Erro " + responseEntity.getStatusCode().toString());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "Erro no envio da Requisição", WSMensagemErroEnum.GENENDPOINT, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex);
        } finally {
            LogWS.montaLog(integrador, log);
        }

        return (T) result;
    }

    private HttpHeaders montaHeader(WSIntegrador integrador, MediaType mediaType) throws ErrorException {

        HttpHeaders result = null;
        result = new HttpHeaders();

        try {
            String encodekey = integrador.getDsCredencialList().get(1) + ":" + integrador.getDsCredencialList().get(2);
            byte[] bytesEncoded = Base64.getEncoder().encode(encodekey.getBytes());
            String encoded = new String(bytesEncoded);

            result.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            if (mediaType != null) {
                result.setContentType(mediaType);
            } else {
                result.setContentType(MediaType.APPLICATION_JSON);
            }

            if (integrador.getSessao() != null) {
                result.set("Authorization", "Bearer " + integrador.getSessao().getDsSessao());
            } else {
                result.set("Authorization", "Basic " + encoded);
            }
        } catch (Exception ex) {
            throw new ErrorException("Erro ao montar o header");
        }
        return result;
    }

    public void verificaErro(WSIntegrador integrador, ResponseEntity<String> response) throws ErrorException {
        String dsBody = null;

        if (response != null) {
            dsBody = (String) response.getBody();

            if (dsBody != null) {
                String dsMensagem = "";
                ErrorBase error = gson.fromJson(dsBody, ErrorBase.class);

                if (error.getDetails() != null && !error.getDetails().isEmpty()) {
                    for (Details detalhe : error.getDetails()) {
                        if (detalhe.getError_code() != null || detalhe.getDescription() != null) {
                            dsMensagem = (dsMensagem != null ? dsMensagem + " - " + detalhe.getError_code() + " - " + detalhe.getDescription() : detalhe.getError_code() + " - " + detalhe.getDescription());
                        } else {
                            if (error.getName() != null) {
                                dsMensagem = error.getStatus_code() + " - " + error.getName() + " - " + error.getMessage();
                            } else {
                                dsMensagem = error.getError() + " - " + error.getError_description();
                            }
                        }
                    }
                } else if (error.getError() != null) {
                    if (error.getName() != null) {
                        dsMensagem = error.getStatus_code() + " - " + error.getName() + " - " + error.getMessage();
                    } else {
                        dsMensagem = error.getError() + " - " + error.getError_description();
                    }
                }

                if (!dsMensagem.equals("")) {
                    throw new ErrorException(integrador, RESTClient.class, "verificaErro", WSMensagemErroEnum.GENMETHOD, "Erro do conector: " + dsMensagem, WSIntegracaoStatusEnum.NEGADO, null);
                }
            }
        }
    }
}
