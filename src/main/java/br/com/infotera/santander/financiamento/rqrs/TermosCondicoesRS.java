/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.financiamento.rqrs;

import br.com.infotera.santander.financiamento.model.Data;

/**
 *
 * @author William Dias
 */
public class TermosCondicoesRS {
    
    private Data data;
    private String firstTimeFilling;
    private String showQuestions; // Se ja respondeu as questões (boolean)
    private String answerCompanyOffer; // Indica se o cliente aceitou os termos de aceite (TermosCondicoes)
    private String answerGroupOffer; // Indica se o cliente aceita questão sobre grupos (LGPD)
    private String answerPartnerOffer; // Indica se o cliente aceita questão sobre o idParceiro (LGPD)
    private String parameterFirstLineCompanyOffer;
    private String parameterSecondLineCompanyOffer;
    private String parameterFirstLineSantanderGroupOffer;
    private String parameterSecondLineSantanderGroupOffer;
    private String parameterFirstLinePartnerOffer;
    private String parameterSecondLinePartnerOffer;
    private String partnerUser;

    public TermosCondicoesRS() {
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getFirstTimeFilling() {
        return firstTimeFilling;
    }

    public void setFirstTimeFilling(String firstTimeFilling) {
        this.firstTimeFilling = firstTimeFilling;
    }

    public String getShowQuestions() {
        return showQuestions;
    }

    public void setShowQuestions(String showQuestions) {
        this.showQuestions = showQuestions;
    }

    public String getAnswerCompanyOffer() {
        return answerCompanyOffer;
    }

    public void setAnswerCompanyOffer(String answerCompanyOffer) {
        this.answerCompanyOffer = answerCompanyOffer;
    }

    public String getAnswerGroupOffer() {
        return answerGroupOffer;
    }

    public void setAnswerGroupOffer(String answerGroupOffer) {
        this.answerGroupOffer = answerGroupOffer;
    }

    public String getAnswerPartnerOffer() {
        return answerPartnerOffer;
    }

    public void setAnswerPartnerOffer(String answerPartnerOffer) {
        this.answerPartnerOffer = answerPartnerOffer;
    }

    public String getParameterFirstLineCompanyOffer() {
        return parameterFirstLineCompanyOffer;
    }

    public void setParameterFirstLineCompanyOffer(String parameterFirstLineCompanyOffer) {
        this.parameterFirstLineCompanyOffer = parameterFirstLineCompanyOffer;
    }

    public String getParameterSecondLineCompanyOffer() {
        return parameterSecondLineCompanyOffer;
    }

    public void setParameterSecondLineCompanyOffer(String parameterSecondLineCompanyOffer) {
        this.parameterSecondLineCompanyOffer = parameterSecondLineCompanyOffer;
    }

    public String getParameterFirstLineSantanderGroupOffer() {
        return parameterFirstLineSantanderGroupOffer;
    }

    public void setParameterFirstLineSantanderGroupOffer(String parameterFirstLineSantanderGroupOffer) {
        this.parameterFirstLineSantanderGroupOffer = parameterFirstLineSantanderGroupOffer;
    }

    public String getParameterSecondLineSantanderGroupOffer() {
        return parameterSecondLineSantanderGroupOffer;
    }

    public void setParameterSecondLineSantanderGroupOffer(String parameterSecondLineSantanderGroupOffer) {
        this.parameterSecondLineSantanderGroupOffer = parameterSecondLineSantanderGroupOffer;
    }

    public String getParameterFirstLinePartnerOffer() {
        return parameterFirstLinePartnerOffer;
    }

    public void setParameterFirstLinePartnerOffer(String parameterFirstLinePartnerOffer) {
        this.parameterFirstLinePartnerOffer = parameterFirstLinePartnerOffer;
    }

    public String getParameterSecondLinePartnerOffer() {
        return parameterSecondLinePartnerOffer;
    }

    public void setParameterSecondLinePartnerOffer(String parameterSecondLinePartnerOffer) {
        this.parameterSecondLinePartnerOffer = parameterSecondLinePartnerOffer;
    }

    public String getPartnerUser() {
        return partnerUser;
    }

    public void setPartnerUser(String partnerUser) {
        this.partnerUser = partnerUser;
    }
    
}
