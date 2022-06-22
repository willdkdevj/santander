package br.com.infotera.santander.financiamento.model;

public class FirstInstallment {

    private String maximumDate;

    private String minimumDate;

    private String recommendedDate;

    public FirstInstallment() {
    }

    public FirstInstallment(String maximumDate, String minimumDate, String recommendedDate) {
        this.maximumDate = maximumDate;
        this.minimumDate = minimumDate;
        this.recommendedDate = recommendedDate;
    }

    public String getMaximumDate() {
        return maximumDate;
    }

    public void setMaximumDate(String maximumDate) {
        this.maximumDate = maximumDate;
    }

    public String getMinimumDate() {
        return minimumDate;
    }

    public void setMinimumDate(String minimumDate) {
        this.minimumDate = minimumDate;
    }

    public String getRecommendedDate() {
        return recommendedDate;
    }

    public void setRecommendedDate(String recommendedDate) {
        this.recommendedDate = recommendedDate;
    }
}
