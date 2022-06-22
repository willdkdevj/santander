package br.com.infotera.santander.financiamento.model;

public class Payment {

    private Double entryValue;
    private Double financedValue;
    private Boolean hasInsurance;
    private Integer insuranceId;
    private String registrationFeeType;
    private Integer installmentAmount;
    private String paymentPlan;
    private String firstInstallmentDueDate;
    private String billShippingOptions;

    public Payment() {
    }

    public Double getEntryValue() {
        return entryValue;
    }

    public void setEntryValue(Double entryValue) {
        this.entryValue = entryValue;
    }

    public Double getFinancedValue() {
        return financedValue;
    }

    public void setFinancedValue(Double financedValue) {
        this.financedValue = financedValue;
    }

    public Boolean getHasInsurance() {
        return hasInsurance;
    }

    public void setHasInsurance(Boolean hasInsurance) {
        this.hasInsurance = hasInsurance;
    }

    public Integer getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(Integer insuranceId) {
        this.insuranceId = insuranceId;
    }

    public String getRegistrationFeeType() {
        return registrationFeeType;
    }

    public void setRegistrationFeeType(String registrationFeeType) {
        this.registrationFeeType = registrationFeeType;
    }

    public Integer getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(Integer installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public String getPaymentPlan() {
        return paymentPlan;
    }

    public void setPaymentPlan(String paymentPlan) {
        this.paymentPlan = paymentPlan;
    }

    public String getFirstInstallmentDueDate() {
        return firstInstallmentDueDate;
    }

    public void setFirstInstallmentDueDate(String firstInstallmentDueDate) {
        this.firstInstallmentDueDate = firstInstallmentDueDate;
    }

    public String getBillShippingOptions() {
        return billShippingOptions;
    }

    public void setBillShippingOptions(String billShippingOptions) {
        this.billShippingOptions = billShippingOptions;
    }
    
}
