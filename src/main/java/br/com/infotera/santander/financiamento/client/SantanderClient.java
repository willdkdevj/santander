package br.com.infotera.santander.financiamento.client;

import br.com.infotera.santander.financiamento.rqrs.AuthTokenRQ;
import br.com.infotera.santander.financiamento.rqrs.PreAnaliseRS;
import br.com.infotera.santander.financiamento.rqrs.SimulacaoRQ;
import br.com.infotera.santander.financiamento.rqrs.IntegrationCodeRS;
import br.com.infotera.santander.financiamento.rqrs.TermosCondicoesRQ;
import br.com.infotera.santander.financiamento.rqrs.PropostaRS;
import br.com.infotera.santander.financiamento.rqrs.TermosCondicoesRS;
import br.com.infotera.santander.financiamento.rqrs.FormalizacaoDOCRQ;
import br.com.infotera.santander.financiamento.rqrs.PreAnaliseRQ;
import br.com.infotera.santander.financiamento.rqrs.AuthTokenRS;
import br.com.infotera.santander.financiamento.rqrs.FormalizacaoDOCRS;
import br.com.infotera.santander.financiamento.rqrs.PropostaRQ;
import br.com.infotera.santander.financiamento.rqrs.SimulacaoRS;
import br.com.infotera.common.ErrorException;
import br.com.infotera.common.WSIntegrador;
import br.com.infotera.common.WSSessao;
import br.com.infotera.common.enumerator.WSIntegracaoStatusEnum;
import br.com.infotera.common.enumerator.WSMensagemErroEnum;
import br.com.infotera.common.util.Utils;
import br.com.infotera.santander.financiamento.model.DocumentGet;
import br.com.infotera.santander.financiamento.model.RuleGet;
import br.com.infotera.santander.financiamento.model.Store;
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
                Store store = result.getStores().stream()
                        .filter(storeid -> !storeid.getStatusCode().equals("A")) // A - ATIVO / I - INATIVO
                        .findFirst()
                        .orElse(null);
                
                if(store != null && store.getCode() != null && store.getDocumentNumber() != null
                        && !store.getCode().equals("") && !store.getDocumentNumber().equals("")){
                    
                    // Criação de sessão na primeira chamada
                    sessao = new WSSessao();
                    sessao.setCdChave(store.getCode() + "#" + store.getDocumentNumber() + "#" + store.getName()); // StoreID (Code) / DocumentNumber / Nome Loja (Cliente)
                    sessao.setDtInclusao(new Date());
                    sessao.setIdEmpresa(Integer.parseInt(storeId));
                    
                    authToken.setStoreId(store.getCode());
                    
                } else {
                    throw new ErrorException(integrador, RESTClient.class, "abrirSessao", WSMensagemErroEnum.GENABRSES, 
                            "Erro ao obter o código (StoreID) referente ao tipo de negócio. Entre em contato com o fornecedor (Santander)", WSIntegracaoStatusEnum.NEGADO, null, false);
                }
                
                // 2ª Chamada para obter o access token (Bearer)
                result = restClient.sendReceive(integrador, authToken, HttpMethod.POST, "token", AuthTokenRS.class);
                
                if(result.getAccessToken() != null && !result.getAccessToken().equals("")){
                    sessao.setDsSessao(result.getAccessToken());
                } else {
                    throw new ErrorException(integrador, RESTClient.class, "abrirSessao", WSMensagemErroEnum.GENABRSES, 
                        "Erro ao obter o token (AccessToken) para validar sessão. Entre em contato com o fornecedor (Santander)", WSIntegracaoStatusEnum.NEGADO, null, false);
                }
            } else {
                throw new ErrorException(integrador, RESTClient.class, "abrirSessao", WSMensagemErroEnum.GENABRSES, 
                        "Erro ao obter os códigos (StoreID) para o cliente. Entre em contato com o fornecedor (Santander)", WSIntegracaoStatusEnum.NEGADO, null, false);
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
        String[] codigoIntegra = null;
        IntegrationCodeRS integrationCode = null;
        integrador.setDsAction("products");
        
        try {
            codigoIntegra = integrador.getSessao().getCdChave().split("#");
            result = restClient.sendReceive(integrador, null, HttpMethod.GET, "products"+ "/" + codigoIntegra[0], List.class);
            
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
                        .orElseThrow(ErrorException::new);
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
    
    public TermosCondicoesRS retornarTermosLGPD(WSIntegrador integrador) throws ErrorException {
        TermosCondicoesRS result = null;
        TermosCondicoesRQ termoLgpd = null;
        integrador.setDsAction("consent-register");
        
        try {
            String[] codigoIntegra = integrador.getSessao().getCdChave().split("#");
            if(codigoIntegra.length > 1){
                termoLgpd = new TermosCondicoesRQ();
                termoLgpd.setDocumentNumber(codigoIntegra[1]);
            } else {
                throw new ErrorException(integrador, RESTClient.class, "retornarTermosLGPD", WSMensagemErroEnum.GENCONEC, 
                    "Não foi possível obter o DocumentNumber - Realize nova autenticação para renovar sessão", WSIntegracaoStatusEnum.NEGADO, null, false);
            }
            
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
