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
            // Chamada para obter os códigos (StoreID)
            result = restClient.sendReceive(integrador, authToken, HttpMethod.POST, "auth", AuthTokenRS.class);
            sessao = new WSSessao(result.getTokenType(), integrador.getIdEmpresa(), result.getAccessToken(), new Date(), null);
            if(result.getStores() != null && !Utils.isListNothing(result.getStores())){
                // Verifica o retorno dos códigos 
                storeId = result.getStores().stream()
                        .filter(store -> store.getName().equalsIgnoreCase("turismo"))
                        .findFirst()
                        .orElse(null)
                        .getCode();
                
                if(storeId != null){
                    authToken.setStoreId(storeId);
                    // Chamada para obter o access token (Bearer)
                    result = restClient.sendReceive(integrador, authToken, HttpMethod.POST, "auth", AuthTokenRS.class);
                    
                    // Criação de sessão após o retorno da segunda chamada
                    sessao = new WSSessao(storeId, integrador.getIdEmpresa(), result.getAccessToken(), new Date(), null);
                } else {
                    throw new ErrorException(integrador, RESTClient.class, "abrirSessao", WSMensagemErroEnum.ADI, "Erro ao obter o código (StoreID) referente ao tipo de negócio. Entre em contato com o fornecedor (Santander)", WSIntegracaoStatusEnum.NEGADO, null, true);
                }
            } //else {
//                throw new ErrorException(integrador, RESTClient.class, "abrirSessao", WSMensagemErroEnum.ADI, "Erro ao obter os códigos (StoreID) para o cliente. Entre em contato com o fornecedor (Santander)", WSIntegracaoStatusEnum.NEGADO, null, true);
//            }
            
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "abrirSessao", WSMensagemErroEnum.ADI, "Erro ao realizar Autenticação" + ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return sessao;
    }

    public IntegrationCodeRS identificarTab(WSIntegrador integrador) throws ErrorException {
        List result = null;
        String codigoIntegra = null;
        IntegrationCodeRS integrationCode = null;
        integrador.setDsAction("products");
        
        try {
            codigoIntegra = integrador.getSessao().getCdChave();
            result = restClient.sendReceive(integrador, null, HttpMethod.GET, "products"+ "/" + codigoIntegra, List.class);
            
            List<IntegrationCodeRS> listCode = new ArrayList();
            
            for (int i = 0; i < result.size(); i++) {
                JsonObject object = gson.toJsonTree(result.get(i)).getAsJsonObject();
                integrationCode = gson.fromJson(object, IntegrationCodeRS.class);
                listCode.add(integrationCode);
            }
            
            integrationCode = listCode.stream()
                    .filter(code -> code.getCode().equals("CSC"))
                    .findFirst()
                    .get();
            
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "Erro ao retornar o Código TAB (IntegrationCode)", WSMensagemErroEnum.ADI, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
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
            throw new ErrorException(integrador, RESTClient.class, "Erro ao retornar o Código TAB (IntegrationCode)", WSMensagemErroEnum.ADI, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return result;
    }
    
    public PreAnaliseRS retornarTermosLGPD(WSIntegrador integrador, String numberDoc) throws ErrorException {
        PreAnaliseRS result = null;
        integrador.setDsAction("consent-register");
        
        try {
            PreAnaliseRQ preAnalise = new PreAnaliseRQ();
            preAnalise.setDocumentNumber(numberDoc);
            
            result = restClient.sendReceive(integrador, preAnalise, HttpMethod.POST, "consent-register", PreAnaliseRS.class);
            
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "Erro ao retornar o Código TAB (IntegrationCode)", WSMensagemErroEnum.ADI, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return result;
    }
    
    public InstallmentAmountRS retornarParcelas(WSIntegrador integrador, Integer storeID) throws ErrorException {
        List result = null;
        InstallmentAmountRS installmentAmountRS = null;

        try {
            InstallmentAmountRQ installmentAmountRQ = new InstallmentAmountRQ(storeID);
            result = restClient.sendReceive(integrador, installmentAmountRQ, HttpMethod.GET, String.valueOf(installmentAmountRQ.getStoreId()) + "/" + "installment-amount", List.class);
            
            JsonObject jsonObject = gson.toJsonTree(result.get(0)).getAsJsonObject();
            installmentAmountRS = gson.fromJson(jsonObject, InstallmentAmountRS.class);
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "Erro ao obter a quantidade máxima de parcelas (StoreID)", WSMensagemErroEnum.ADI, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return installmentAmountRS;
    }

    public List<RuleGet> retornarProductCode(WSIntegrador integrador, String idProductCode) throws ErrorException {
        List result = null;
        List<RuleGet> productCodeList = new ArrayList<>();

        try {
            result = restClient.sendReceive(integrador, null, HttpMethod.GET, "forms-payment" + "/" + idProductCode, List.class);
            
            result.stream().map(pc -> {
               JsonObject jsonObject = gson.toJsonTree(pc).getAsJsonObject();
               RuleGet productCode = gson.fromJson(jsonObject, RuleGet.class);
               return productCode;
            }).forEachOrdered(productCode -> {
                productCodeList.add((RuleGet) productCode);
            });
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "Erro ao retornar o Código TAB (IntegrationCode)", WSMensagemErroEnum.ADI, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return !Utils.isListNothing(productCodeList) ? productCodeList : null;
    }

    public List<DocumentGet> retornarGrupoAtividadeEconomica(WSIntegrador integrador) throws ErrorException {
        List result = null;
        List<DocumentGet> atividadeEconomicaList = new ArrayList<>();

        try {
            result = restClient.sendReceive(integrador, null, HttpMethod.GET, "economicActivityGroup", List.class);
            
            result.stream().map(pc -> {
               JsonObject jsonObject = gson.toJsonTree(pc).getAsJsonObject();
               DocumentGet document = gson.fromJson(jsonObject, DocumentGet.class);
               return document;
            }).forEachOrdered(document -> {
                atividadeEconomicaList.add((DocumentGet) document);
            });
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "Erro ao obter a Lista de Grupos de Atividades Econônicas", WSMensagemErroEnum.ADI, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return !Utils.isListNothing(atividadeEconomicaList) ? atividadeEconomicaList : null;
    }
    
    public List<RuleGet> retornarAtividadeEconomica(WSIntegrador integrador, String idGroup) throws ErrorException {
        List result = null;
        List<RuleGet> atividadeEconomicaList = new ArrayList<>();

        try {
            result = restClient.sendReceive(integrador, null, HttpMethod.GET, "economicActivity" + "/" + idGroup, List.class);
            
            result.stream().map(pc -> {
               JsonObject jsonObject = gson.toJsonTree(pc).getAsJsonObject();
               RuleGet productCode = gson.fromJson(jsonObject, RuleGet.class);
               return productCode;
            }).forEachOrdered(productCode -> {
                atividadeEconomicaList.add((RuleGet) productCode);
            });
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "Erro ao retornar a Lista de Atividades Econônimicas", WSMensagemErroEnum.ADI, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return !Utils.isListNothing(atividadeEconomicaList) ? atividadeEconomicaList : null;
    }
    
    public List<DocumentGet> retornarTipoDocumento(WSIntegrador integrador) throws ErrorException {
        List result = null;
        List<DocumentGet> documentList = new ArrayList<>();

        try {
            result = restClient.sendReceive(integrador, null, HttpMethod.GET, "documentType", List.class);
            
            result.stream().map(pc -> {
               JsonObject jsonObject = gson.toJsonTree(pc).getAsJsonObject();
               DocumentGet document = gson.fromJson(jsonObject, DocumentGet.class);
               return document;
            }).forEachOrdered(document -> {
                documentList.add((DocumentGet) document);
            });
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "Erro ao obter a lista de Tipos de Documentos", WSMensagemErroEnum.ADI, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return !Utils.isListNothing(documentList) ? documentList : null;
    }

    public List<RuleGet> retornarCodOcupacao(WSIntegrador integrador) throws ErrorException {
        List result = null;
        List<RuleGet> ocupacaoList = new ArrayList<>();

        try {
            result = restClient.sendReceive(integrador, null, HttpMethod.GET, "role", List.class);
            
            result.stream().map(pc -> {
               JsonObject jsonObject = gson.toJsonTree(pc).getAsJsonObject();
               DocumentGet document = gson.fromJson(jsonObject, DocumentGet.class);
               return document;
            }).forEachOrdered(document -> {
                ocupacaoList.add((RuleGet) document);
            });
        } catch(ErrorException e) {
            throw e;
        } catch (Exception ex) {
            integrador.setDsMensagem(ex.getMessage());
            integrador.setIntegracaoStatus(WSIntegracaoStatusEnum.NEGADO);
            throw new ErrorException(integrador, RESTClient.class, "Erro ao obter a Lista das Profissões", WSMensagemErroEnum.ADI, ex.getMessage(), WSIntegracaoStatusEnum.NEGADO, ex, false);
        }
        
        return !Utils.isListNothing(ocupacaoList) ? ocupacaoList : null;
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
