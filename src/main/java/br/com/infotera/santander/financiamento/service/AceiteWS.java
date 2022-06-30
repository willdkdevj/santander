/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.financiamento.service;

import br.com.infotera.common.ErrorException;
import br.com.infotera.common.WSIntegrador;
import br.com.infotera.common.enumerator.WSIntegracaoStatusEnum;
import br.com.infotera.common.enumerator.WSMensagemErroEnum;
import br.com.infotera.common.pagto.WSPagtoMeioFinanciamento;
import br.com.infotera.common.pagto.financiamento.WSProdutoFinanciamento;
import br.com.infotera.common.pagto.financiamento.WSTermoFinanciamento;
import br.com.infotera.common.pagto.financiamento.rqrs.WSPagtoAnaliseRQ;
import br.com.infotera.common.pagto.financiamento.rqrs.WSPagtoAnaliseRS;
import br.com.infotera.santander.financiamento.client.SantanderClient;
import br.com.infotera.santander.financiamento.rqrs.IntegrationCodeRS;
import br.com.infotera.santander.financiamento.rqrs.TermosCondicoesRS;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author william
 */
public class AceiteWS {
    
    @Autowired
    private SessaoWS sessaoWS;

    @Autowired
    private SantanderClient santanderClient;

    public WSPagtoAnaliseRS apresentarTermos(WSPagtoAnaliseRQ pagtoAnaliseRQ) throws ErrorException {
        WSIntegrador integrador = null;
        if (pagtoAnaliseRQ.getIntegrador().getSessao() == null) {
            integrador = sessaoWS.abreSessao(pagtoAnaliseRQ.getIntegrador());
        } else {
            integrador = pagtoAnaliseRQ.getIntegrador();
        }
        
        // Devolver informações sobre Termos Gerais e LGPD e a Lista de Produtos para Financiamento
        WSPagtoMeioFinanciamento pagtoMeio = montarPagtoMeioFinanciamento(integrador);
        
        return new WSPagtoAnaliseRS(integrador, pagtoMeio, WSIntegracaoStatusEnum.OK);
    }
            
    public WSPagtoMeioFinanciamento montarPagtoMeioFinanciamento(WSIntegrador integrador) throws ErrorException{
        WSPagtoMeioFinanciamento meioPagto = null;
        
        // Chamada para obter o Código da Loja (ID Loja) - Deve ser realizada nova chamada para obter novo código, caso seja feita nova análise
        List<IntegrationCodeRS> integrationCodeList = santanderClient.getProductCSC(integrador);
        
        // Retorna os termos e condições para uso do produto de financiamento (Retorno de Contrato no formato HTML)
        TermosCondicoesRS termosCondGeral = santanderClient.retornarTermosCondicoes(integrador); // VERIFICAR ONDE DEVERA SER PASSADA O TERMO (HTML)
        
        // Retorna as questões a serem apresentadas ao cliente e seu aceite deve ser avaliada para realizar a simulação
        TermosCondicoesRS termosLGPD = santanderClient.retornarTermosLGPD(integrador); // VERIFICAR ONDE DEVERA SER PASSADA AS QUESTÕES SOBRE O TERMO (TEXTO)
        
        try {
            List<WSTermoFinanciamento> termoList = obterTermosFinanciamento(integrador, termosCondGeral, termosLGPD);
            
            List<WSProdutoFinanciamento> produtoList = obterListaDeProdutos(integrador, integrationCodeList);
            
            meioPagto = new WSPagtoMeioFinanciamento();
            meioPagto.setProdutoFinanciamentoList(produtoList);
            meioPagto.setTermoFinanciamentoList(termoList);
            
        } catch(Exception ex){
            throw new ErrorException(integrador, AceiteWS.class, "montarPagtoMeioFinanciamento", 
                WSMensagemErroEnum.ADI, "Erro ao obter os termos e produtos de financiamento.", WSIntegracaoStatusEnum.INCONSISTENTE, ex, false);
        }
        
        return meioPagto;
    }

    private List<WSTermoFinanciamento> obterTermosFinanciamento(WSIntegrador integrador, TermosCondicoesRS termosCondGeral, TermosCondicoesRS termosLGPD) throws ErrorException {
        List<WSTermoFinanciamento> termoList = new ArrayList<>();
        try {
            termosCondGeral.getData().getListTerms().forEach(term -> {
                WSTermoFinanciamento termo = new WSTermoFinanciamento();
                termo.setId(Integer.parseInt(term.getId()));
                termo.setCdTermo(term.getCode());
                termo.setDsTermo(term.getDescription());
                termo.setPrimeiroForm(Boolean.parseBoolean(termosLGPD.getFirstTimeFilling()));
                termo.setMostrarQuestoes(Boolean.parseBoolean(termosLGPD.getShowQuestions()));
                
                if(termosLGPD.getAnswerCompanyOffer().equals("S")){
                    termo.setPrLinOfertaCompanhia(termosLGPD.getParameterFirstLineCompanyOffer());
                    termo.setSeLinOfertaCompanhia(termosLGPD.getParameterSecondLineCompanyOffer());
                }
                
                if(termosLGPD.getAnswerGroupOffer().equals("S")){
                    termo.setPrLinOfertaGrupo(termosLGPD.getParameterFirstLineSantanderGroupOffer());
                    termo.setSeLinOfertaGrupo(termosLGPD.getParameterSecondLineSantanderGroupOffer());
                }
                
                if(termosLGPD.getAnswerPartnerOffer().equals("S")){
                    termo.setPrLinOfertaParceiro(termosLGPD.getParameterFirstLinePartnerOffer());
                    termo.setSeLinOfertaParceiro(termosLGPD.getParameterSecondLinePartnerOffer());
                }
                
                termoList.add(termo);
            });
            
        } catch(Exception ex){
            throw new ErrorException(integrador, AceiteWS.class, "obterTermosFinanciamento", 
                WSMensagemErroEnum.ADI, "Erro ao obter os Termos de Financiamento", WSIntegracaoStatusEnum.INCONSISTENTE, ex, false);     
        }
            
        return termoList;
    }

    private List<WSProdutoFinanciamento> obterListaDeProdutos(WSIntegrador integrador, List<IntegrationCodeRS> integrationCodeList) throws ErrorException {
        List<WSProdutoFinanciamento> produtList = new ArrayList<>();
        try {
            integrationCodeList.forEach(produto -> {
                WSProdutoFinanciamento prodFinanciamento = null;
                if(produto.getIsEnabledForStore()){
                    prodFinanciamento = new WSProdutoFinanciamento();
                    prodFinanciamento.setId(produto.getId());
                    prodFinanciamento.setCdProduto(produto.getCode());
                    prodFinanciamento.setDsProduto(produto.getDesc());
                    prodFinanciamento.setTpCliente(produto.getIsEnabledForClientType());
                }
                if(prodFinanciamento != null){
                    produtList.add(prodFinanciamento);
                }
            });
        } catch(Exception ex){
            throw new ErrorException(integrador, AceiteWS.class, "obterTermosFinanciamento", 
                WSMensagemErroEnum.ADI, "Erro ao obter os Produtos de Financiamento", WSIntegracaoStatusEnum.INCONSISTENTE, ex, false);     
        }
        
        return produtList;
    }
}
