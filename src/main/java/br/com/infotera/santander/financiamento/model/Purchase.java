package br.com.infotera.santander.financiamento.model;

public class Purchase {

    private String financedObject;

    private Double entryValue;

    private Double installmentValue;

    private Integer installmentQuantity;

    private String travelDate;

    private Integer subsegment;

    private Double value;

    public Purchase() {
    }

    public Purchase(String financedObject, Double entryValue, Double installmentValue, Integer installmentQuantity, String travelDate, Integer subsegment, Double value) {
        this.financedObject = financedObject;
        this.entryValue = entryValue;
        this.installmentValue = installmentValue;
        this.installmentQuantity = installmentQuantity;
        this.travelDate = travelDate;
        this.subsegment = subsegment;
        this.value = value;
    }

    public String getFinancedObject() {
        return financedObject;
    }

    public void setFinancedObject(String financedObject) {
        this.financedObject = financedObject;
    }

    public Double getEntryValue() {
        return entryValue;
    }

    public void setEntryValue(Double entryValue) {
        this.entryValue = entryValue;
    }

    public Double getInstallmentValue() {
        return installmentValue;
    }

    public void setInstallmentValue(Double installmentValue) {
        this.installmentValue = installmentValue;
    }

    public Integer getInstallmentQuantity() {
        return installmentQuantity;
    }

    public void setInstallmentQuantity(Integer installmentQuantity) {
        this.installmentQuantity = installmentQuantity;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public Integer getSubsegment() {
        return subsegment;
    }

    public void setSubsegment(Integer subsegment) {
        this.subsegment = subsegment;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
