package br.com.infotera.santander.financiamento.model;

public class Entry {

    private Double maximumValue;

    private Double minimumValue;

    private Double recommendedValue;

    public Entry() {
    }

    public Entry(Double maximumValue, Double minimumValue, Double recommendedValue) {
        this.maximumValue = maximumValue;
        this.minimumValue = minimumValue;
        this.recommendedValue = recommendedValue;
    }

    public Double getMaximumValue() {
        return maximumValue;
    }

    public void setMaximumValue(Double maximumValue) {
        this.maximumValue = maximumValue;
    }

    public Double getMinimumValue() {
        return minimumValue;
    }

    public void setMinimumValue(Double minimumValue) {
        this.minimumValue = minimumValue;
    }

    public Double getRecommendedValue() {
        return recommendedValue;
    }

    public void setRecommendedValue(Double recommendedValue) {
        this.recommendedValue = recommendedValue;
    }
}
