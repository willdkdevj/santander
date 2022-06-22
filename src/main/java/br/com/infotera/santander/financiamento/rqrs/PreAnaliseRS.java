package br.com.infotera.santander.financiamento.rqrs;

public class PreAnaliseRS {

    private Boolean firstTimeFilling; // true,
    private Boolean showQuestions; // false,
    private String answerCompanyOffer; // "S",
    private String answerGroupOffer; // "S",
    private String answerPartnerOffer; // "S",
    private String parameterFirstLineCompanyOffer; 
    private String parameterSecondLineCompanyOffer; 
    private String parameterFirstLineSantanderGroupOffer;
    private String parameterSecondLineSantanderGroupOffer;
    private String parameterFirstLinePartnerOffer;
    private String parameterSecondLinePartnerOffer;
    private String partnerUser;

    public PreAnaliseRS() {
    }

    public Boolean getFirstTimeFilling() {
        return firstTimeFilling;
    }

    public void setFirstTimeFilling(Boolean firstTimeFilling) {
        this.firstTimeFilling = firstTimeFilling;
    }

    public Boolean getShowQuestions() {
        return showQuestions;
    }

    public void setShowQuestions(Boolean showQuestions) {
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
