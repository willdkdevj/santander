package br.com.infotera.santander.client;

import br.com.infotera.common.ErrorException;
import br.com.infotera.common.WSIntegrador;
import br.com.infotera.common.WSSessao;
import br.com.infotera.common.enumerator.WSIntegracaoStatusEnum;
import br.com.infotera.common.enumerator.WSMensagemErroEnum;
import br.com.infotera.common.util.Utils;
import br.com.infotera.santander.model.DocumentGet;
import br.com.infotera.santander.model.RQRS.*;
import br.com.infotera.santander.model.RuleGet;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SantanderClient {

    @Autowired
    private RESTClient restClient;
    
    @Autowired
    private Gson gson;

    public WSSessao abrirSessao(WSIntegrador integrador, AuthTokenRQ authToken) throws ErrorException {
        AuthTokenRS result = null;
        WSSessao sessao = null;
        String storeId = null;
        integrador.setDsAction("token");
        
        try {
            // 1ª Chamada para obter os códigos (StoreID)
            result = restClient.sendReceive(integrador, authToken, HttpMethod.POST, "token", AuthTokenRS.class);
//            sessao = new WSSessao(result.getTokenType(), integrador.getIdEmpresa(), result.getAccessToken(), new Date(), null);
            if(result.getStores() != null && !Utils.isListNothing(result.getStores())){
                // Verifica o retorno dos códigos 
                storeId = result.getStores().stream()
                        .filter(store -> !store.getCode().equals(""))
                        .findFirst()
                        .orElse(null)
                        .getCode();
                
                if(storeId != null){
                    authToken.setStoreId(storeId);
                        
                    // 2ª Chamada para obter o access token (Bearer)
                    result = restClient.sendReceive(integrador, authToken, HttpMethod.POST, "token", AuthTokenRS.class);
                    
                    // Criação de sessão após o retorno da segunda chamada
                    sessao = new WSSessao();
                    sessao.setCdChave(storeId);
                    sessao.setDtInclusao(new Date());
                    sessao.setIdEmpresa(integrador.getIdEmpresa());
                    sessao.setDsSessao(result.getAccessToken());
                } else {
                    throw new ErrorException(integrador, RESTClient.class, "abrirSessao", WSMensagemErroEnum.GENABRSES, 
                            "Erro ao obter o código (StoreID) referente ao tipo de negócio. Entre em contato com o fornecedor (Santander)", WSIntegracaoStatusEnum.NEGADO, null, true);
                }
            } else {
                throw new ErrorException(integrador, RESTClient.class, "abrirSessao", WSMensagemErroEnum.GENABRSES, 
                        "Erro ao obter os códigos (StoreID) para o cliente. Entre em contato com o fornecedor (Santander)", WSIntegracaoStatusEnum.NEGADO, null, true);
            }
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "abrirSessao", WSMensagemErroEnum.GENABRSES, 
                    "Erro ao realizar Autenticação" + ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return sessao;
    }

    public IntegrationCodeRS getProductCSC(WSIntegrador integrador) throws ErrorException {
        List result = null;
        String codigoIntegra = null;
        IntegrationCodeRS integrationCode = null;
        integrador.setDsAction("products");
        
        try {
            codigoIntegra = integrador.getSessao().getCdChave();
            result = restClient.sendReceive(integrador, null, HttpMethod.GET, "products"+ "/" + codigoIntegra, List.class);
            
            List<IntegrationCodeRS> listCode = null;
            if(result != null){
                listCode = new ArrayList();
            
                for (int i = 0; i < result.size(); i++) {
                    JsonObject object = gson.toJsonTree(result.get(i)).getAsJsonObject();
                    integrationCode = gson.fromJson(object, IntegrationCodeRS.class);
                    listCode.add(integrationCode);
                }

                integrationCode = listCode.stream()
                        .filter(code -> code.getCode().equals("CSC"))
                        .findFirst()
                        .get();
            } else {
                throw new ErrorException(integrador, RESTClient.class, "getProductCSC", WSMensagemErroEnum.GENCONEC, 
                    "Erro ao retornar os produtos (Santander - Financiamento) - Entre em contato com o fornecedor", WSIntegracaoStatusEnum.NEGADO, null, false);
            }
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "getProductCSC", WSMensagemErroEnum.GENCONEC, 
                    "Erro ao retornar o produto CSC (Product CSC)" + ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return integrationCode;
    }

    public TermosCondicoesRS retornarTermosCondicoes(WSIntegrador integrador) throws ErrorException {
        TermosCondicoesRS result = null;
        integrador.setDsAction("list-terms");
        
        try {
            result = restClient.sendReceive(integrador, null, HttpMethod.GET, "list-terms", TermosCondicoesRS.class);
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "retornarTermosCondicoes", WSMensagemErroEnum.GENCONEC, 
                    "Erro ao retornar os Termos e Condições para Financiamento " + ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return result;
    }
    
    public TermosCondicoesRS retornarTermosLGPD(WSIntegrador integrador, String numberDoc) throws ErrorException {
        TermosCondicoesRS result = null;
        integrador.setDsAction("consent-register");
        
        try {
            TermosCondicoesRQ termoLgpd = new TermosCondicoesRQ();
            termoLgpd.setDocumentNumber(numberDoc);
            
            result = restClient.sendReceive(integrador, termoLgpd, HttpMethod.POST, "consent-register", TermosCondicoesRS.class);
            
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "retornarTermosLGPD", WSMensagemErroEnum.GENCONEC, 
                    "Erro ao retornar os Termos da Lei Geral de Proteção de Dados (LGPD)" + ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return result;
    }
    
    public SimulacaoRS criarSimulacao(WSIntegrador integrador, SimulacaoRQ simulacaoRQ) throws ErrorException {
        SimulacaoRS result = null;

        try {
            result = restClient.sendReceive(integrador, simulacaoRQ, HttpMethod.POST, "simulation", SimulacaoRS.class);
            
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "criarSimulacao", WSMensagemErroEnum.GENCONEC, 
                    "Erro ao criar a simulação para a reserva" + ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return result;
    }

    public SimulacaoRS buscarProposta(WSIntegrador integrador, String idProductCode) throws ErrorException {
        SimulacaoRS result = null;

        try {
            result = restClient.sendReceive(integrador, null, HttpMethod.GET, "simulation" + "/" + idProductCode, SimulacaoRS.class);
            
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "buscarProposta", WSMensagemErroEnum.GENCONEC, 
                    "Erro ao criar a simulação para a reserva" + ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return result;
    }

    public SimulacaoRS finalizarSimulacao(WSIntegrador integrador, SimulacaoRQ simulacaoRQ) throws ErrorException {
        SimulacaoRS result = null;

        try {
            result = restClient.sendReceive(integrador, simulacaoRQ, HttpMethod.POST, "finish", SimulacaoRS.class);
            
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "finalizarSimulacao", WSMensagemErroEnum.GENCONEC, 
                    "Erro ao finalizar o envio da simulação para proposta de financiamento" + ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return result;
    }
    
    public PropostaRS registrarProposta(WSIntegrador integrador, String proposalId) throws ErrorException {
        PropostaRS result = null;

        try {
            result = restClient.sendReceive(integrador, null, HttpMethod.GET, "register" + "/" + proposalId, PropostaRS.class);
            
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "registrarProposta", WSMensagemErroEnum.GENCONEC, 
                    "Erro ao formalizar simulação (Registro) " + ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return result;
    }
    
    public PropostaRS salvarProposta(WSIntegrador integrador, PropostaRQ propostaRQ) throws ErrorException {
        PropostaRS result = null;

        try {
            result = restClient.sendReceive(integrador, propostaRQ, HttpMethod.POST, "save", PropostaRS.class);
            
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "salvarProposta", WSMensagemErroEnum.GENCONEC, 
                    "Erro ao salvar o registro da Proposta do Financiamento " + ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return result;
    }

    public FormalizacaoDOCRS listarDocumentos(WSIntegrador integrador, FormalizacaoDOCRQ formalizacaoDOCRQ) throws ErrorException {
        FormalizacaoDOCRS result = null;

        try {
            result = restClient.sendReceive(integrador, null, HttpMethod.GET, "ckeck-list", FormalizacaoDOCRS.class);
            
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "listarDocumentos", WSMensagemErroEnum.GENCONEC, 
                    "Erro ao obter as informações dos documentos necessários " + ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return result;
    }

    public List<RuleGet> retornarCodEstadoCivil(WSIntegrador integrador) throws ErrorException {
        List result = null;
        List<RuleGet> estadoCivilList = new ArrayList<>();

        try {
            result = restClient.sendReceive(integrador, null, HttpMethod.GET, "maritalStatus", List.class);
            
            result.stream().map(pc -> {
               JsonObject jsonObject = gson.toJsonTree(pc).getAsJsonObject();
               DocumentGet document = gson.fromJson(jsonObject, DocumentGet.class);
               return document;
            }).forEachOrdered(document -> {
                estadoCivilList.add((RuleGet) document);
            });
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "Erro ao obter a Lista de Estados Cívil", WSMensagemErroEnum.ADI, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return !Utils.isListNothing(estadoCivilList) ? estadoCivilList : null;
    }

    public List<DocumentGet> retornarCodNacionalidade(WSIntegrador integrador) throws ErrorException {
        List result = null;
        List<DocumentGet> nacionalidadeList = new ArrayList<>();

        try {
            result = restClient.sendReceive(integrador, null, HttpMethod.GET, "nationality", List.class);
            
            result.stream().map(pc -> {
               JsonObject jsonObject = gson.toJsonTree(pc).getAsJsonObject();
               DocumentGet document = gson.fromJson(jsonObject, DocumentGet.class);
               return document;
            }).forEachOrdered(document -> {
                nacionalidadeList.add((DocumentGet) document);
            });
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "Erro ao obter a Lista de Nacionalidade", WSMensagemErroEnum.ADI, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return !Utils.isListNothing(nacionalidadeList) ? nacionalidadeList : null;
    }
    
    public List<RuleGet> retornarCodCidade(WSIntegrador integrador, String codEstado) throws ErrorException {
        List result = null;
        List<RuleGet> cidadeList = null;

        try {
            result = restClient.sendReceive(integrador, null, HttpMethod.GET, "naturalness" + "/" + codEstado, List.class);
            
            result.stream().map(pc -> {
               JsonObject jsonObject = gson.toJsonTree(pc).getAsJsonObject();
               DocumentGet document = gson.fromJson(jsonObject, DocumentGet.class);
               return document;
            }).forEachOrdered(document -> {
                cidadeList.add((RuleGet) document);
            });
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "Erro ao obter a Lista de Código de Municipios", WSMensagemErroEnum.ADI, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return !Utils.isListNothing(cidadeList) ? cidadeList : null;
    }

    public PreAnaliseRS preAnalise(WSIntegrador integrador, PreAnaliseRQ preAnaliseRQ) throws ErrorException {
        PreAnaliseRS result = null;
        try {
            result = restClient.sendReceive(integrador, preAnaliseRQ, HttpMethod.POST, "identification" + "/" + "assets", PreAnaliseRS.class);
            
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "Erro ao chamar Pre-Análise", WSMensagemErroEnum.ADI, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return result;
    }

    public SimulacaoRS simulacao(WSIntegrador integrador, SimulacaoRQ simulacaoRQ) throws ErrorException {
        SimulacaoRS result = null;
        try {
            result = restClient.sendReceive(integrador, simulacaoRQ, HttpMethod.POST, "simulation" + "/" + "assets", SimulacaoRS.class);
            
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "Erro ao chamar Simulação", WSMensagemErroEnum.ADI, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return result;
    }

    public PropostaRS proposta(WSIntegrador integrador, PropostaRQ propostaRQ) throws ErrorException {
        PropostaRS result = null;
        try {
            result = restClient.sendReceive(integrador, propostaRQ, HttpMethod.POST, "capture" + "/" + "assets" + "/" + "cdc" + "/" + "proposal",  PropostaRS.class);
            
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex){
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "Erro ao chamar o Capturar (Proposal)", WSMensagemErroEnum.ADI, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return result;
    }

    public SimulacaoRS order(WSIntegrador integrador, SimulacaoRQ simulacaoRQ) throws ErrorException {
        SimulacaoRS result = null;
        try {
            result = restClient.sendReceive(integrador, simulacaoRQ, HttpMethod.POST, "order", SimulacaoRS.class);
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "Erro ao chamar a Simulação", WSMensagemErroEnum.ADI, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        return result;
    }
}
