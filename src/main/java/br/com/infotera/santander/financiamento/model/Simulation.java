/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.financiamento.model;

import java.util.List;

/**
 *
 * @author William Dias
 */
public class Simulation {
    
    private String productCode; //: "CDC",
    private String supplierChannelCode; // Código do Canal do fornecedor
    private Double financedValue; // valor a ser financiado
    private Double entryValue; //  valor de entrada
    private String travelDate; // data da viagem
    private String cupom; // "DJHNWD147FF"
    private Boolean isEnabledToInformIncome; // false,
    private Integer daysToProposalExpiration; // 30,
    private Double maxFinancedValue; // 100000,
    private Double minFinancedValue; 
    private Integer dueDaysIntervalMin; // 10,
    private Integer dueDaysIntervalMax; // 30,
    private Integer defaultDaysToInitialPayment; // 30,
    private List<Terms> accountTypes;
    private List<String> banks;
    private List<Integer> valuesToAdd;
    private List<Terms> paymentForm;
    private List<Terms> paymentMethods;
    private Object insurance; // List<>, String
    private Proposal proposalData;
    private Boolean hasInsurance; // false,
    private Double purchaseValue; // 10000,
    private Double financedTotalValue; // 9669.11,
    private String installmentTotalQuantity; // "24",
    private Double installmentValue; // 510.97,
    private String planType; // "Tradicional",
    private PlanTypeSchema planTypeSchemaData;
    private String firstInstallmentDate; // "2021-09-19",
    private String liquidationType; // "BOLETO",
    private String liquidationTypeCode; // "B",
    private String accountantIndicator; // "N",
    private Double interestRate; // 1.99,
    private Double iof; // 269.11,
    private String registrationFeeType; // "IN_INSTALLMENTS",
    private Boolean registerTariffExemptionFlag; // false,
    private Boolean registerTariffOneTimePaymentFlag; // true,
    private Integer registerTariff; // 400,
    private Double interestRateByYear; // 26.68,
    private Integer insuranceValue; // 0,
    private Double returnRateByYear; // 36.67,
    private Double returnRate; //2.6,
    private Double annualReturnRate; // 36.6727,
    private List<String> conditionPlanFlex; 
    private Personal personalData;
    private Calculation calculationLimit;
    private AccountData accountData;
    private Contract contract;
    private ChannelSupplier channelSupplier;
    private CertifiedAgent certifiedAgent;
    private ShopkeeperData shopkeeperData;
    
    public Simulation() {
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getSupplierChannelCode() {
        return supplierChannelCode;
    }

    public void setSupplierChannelCode(String supplierChannelCode) {
        this.supplierChannelCode = supplierChannelCode;
    }

    public Double getFinancedValue() {
        return financedValue;
    }

    public void setFinancedValue(Double financedValue) {
        this.financedValue = financedValue;
    }

    public Double getEntryValue() {
        return entryValue;
    }

    public void setEntryValue(Double entryValue) {
        this.entryValue = entryValue;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public String getCupom() {
        return cupom;
    }

    public void setCupom(String cupom) {
        this.cupom = cupom;
    }

    public Boolean getIsEnabledToInformIncome() {
        return isEnabledToInformIncome;
    }

    public void setIsEnabledToInformIncome(Boolean isEnabledToInformIncome) {
        this.isEnabledToInformIncome = isEnabledToInformIncome;
    }

    public Integer getDaysToProposalExpiration() {
        return daysToProposalExpiration;
    }

    public void setDaysToProposalExpiration(Integer daysToProposalExpiration) {
        this.daysToProposalExpiration = daysToProposalExpiration;
    }

    public Double getMaxFinancedValue() {
        return maxFinancedValue;
    }

    public void setMaxFinancedValue(Double maxFinancedValue) {
        this.maxFinancedValue = maxFinancedValue;
    }

    public Double getMinFinancedValue() {
        return minFinancedValue;
    }

    public void setMinFinancedValue(Double minFinancedValue) {
        this.minFinancedValue = minFinancedValue;
    }

    public Integer getDueDaysIntervalMin() {
        return dueDaysIntervalMin;
    }

    public void setDueDaysIntervalMin(Integer dueDaysIntervalMin) {
        this.dueDaysIntervalMin = dueDaysIntervalMin;
    }

    public Integer getDueDaysIntervalMax() {
        return dueDaysIntervalMax;
    }

    public void setDueDaysIntervalMax(Integer dueDaysIntervalMax) {
        this.dueDaysIntervalMax = dueDaysIntervalMax;
    }

    public Integer getDefaultDaysToInitialPayment() {
        return defaultDaysToInitialPayment;
    }

    public void setDefaultDaysToInitialPayment(Integer defaultDaysToInitialPayment) {
        this.defaultDaysToInitialPayment = defaultDaysToInitialPayment;
    }

    public List<Terms> getAccountTypes() {
        return accountTypes;
    }

    public void setAccountTypes(List<Terms> accountTypes) {
        this.accountTypes = accountTypes;
    }

    public List<String> getBanks() {
        return banks;
    }

    public void setBanks(List<String> banks) {
        this.banks = banks;
    }

    public List<Integer> getValuesToAdd() {
        return valuesToAdd;
    }

    public void setValuesToAdd(List<Integer> valuesToAdd) {
        this.valuesToAdd = valuesToAdd;
    }

    public List<Terms> getPaymentForm() {
        return paymentForm;
    }

    public void setPaymentForm(List<Terms> paymentForm) {
        this.paymentForm = paymentForm;
    }

    public List<Terms> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<Terms> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public Object getInsurance() {
        return insurance;
    }

    public void setInsurance(Object insurance) {
        this.insurance = insurance;
    }

    public Proposal getProposalData() {
        return proposalData;
    }

    public void setProposalData(Proposal proposalData) {
        this.proposalData = proposalData;
    }

    public Boolean getHasInsurance() {
        return hasInsurance;
    }

    public void setHasInsurance(Boolean hasInsurance) {
        this.hasInsurance = hasInsurance;
    }

    public Double getPurchaseValue() {
        return purchaseValue;
    }

    public void setPurchaseValue(Double purchaseValue) {
        this.purchaseValue = purchaseValue;
    }

    public Double getFinancedTotalValue() {
        return financedTotalValue;
    }

    public void setFinancedTotalValue(Double financedTotalValue) {
        this.financedTotalValue = financedTotalValue;
    }

    public String getInstallmentTotalQuantity() {
        return installmentTotalQuantity;
    }

    public void setInstallmentTotalQuantity(String installmentTotalQuantity) {
        this.installmentTotalQuantity = installmentTotalQuantity;
    }

    public Double getInstallmentValue() {
        return installmentValue;
    }

    public void setInstallmentValue(Double installmentValue) {
        this.installmentValue = installmentValue;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public PlanTypeSchema getPlanTypeSchemaData() {
        return planTypeSchemaData;
    }

    public void setPlanTypeSchemaData(PlanTypeSchema planTypeSchemaData) {
        this.planTypeSchemaData = planTypeSchemaData;
    }

    public String getFirstInstallmentDate() {
        return firstInstallmentDate;
    }

    public void setFirstInstallmentDate(String firstInstallmentDate) {
        this.firstInstallmentDate = firstInstallmentDate;
    }

    public String getLiquidationType() {
        return liquidationType;
    }

    public void setLiquidationType(String liquidationType) {
        this.liquidationType = liquidationType;
    }

    public String getLiquidationTypeCode() {
        return liquidationTypeCode;
    }

    public void setLiquidationTypeCode(String liquidationTypeCode) {
        this.liquidationTypeCode = liquidationTypeCode;
    }

    public String getAccountantIndicator() {
        return accountantIndicator;
    }

    public void setAccountantIndicator(String accountantIndicator) {
        this.accountantIndicator = accountantIndicator;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getIof() {
        return iof;
    }

    public void setIof(Double iof) {
        this.iof = iof;
    }

    public String getRegistrationFeeType() {
        return registrationFeeType;
    }

    public void setRegistrationFeeType(String registrationFeeType) {
        this.registrationFeeType = registrationFeeType;
    }

    public Boolean getRegisterTariffExemptionFlag() {
        return registerTariffExemptionFlag;
    }

    public void setRegisterTariffExemptionFlag(Boolean registerTariffExemptionFlag) {
        this.registerTariffExemptionFlag = registerTariffExemptionFlag;
    }

    public Boolean getRegisterTariffOneTimePaymentFlag() {
        return registerTariffOneTimePaymentFlag;
    }

    public void setRegisterTariffOneTimePaymentFlag(Boolean registerTariffOneTimePaymentFlag) {
        this.registerTariffOneTimePaymentFlag = registerTariffOneTimePaymentFlag;
    }

    public Integer getRegisterTariff() {
        return registerTariff;
    }

    public void setRegisterTariff(Integer registerTariff) {
        this.registerTariff = registerTariff;
    }

    public Double getInterestRateByYear() {
        return interestRateByYear;
    }

    public void setInterestRateByYear(Double interestRateByYear) {
        this.interestRateByYear = interestRateByYear;
    }

    public Integer getInsuranceValue() {
        return insuranceValue;
    }

    public void setInsuranceValue(Integer insuranceValue) {
        this.insuranceValue = insuranceValue;
    }

    public Double getReturnRateByYear() {
        return returnRateByYear;
    }

    public void setReturnRateByYear(Double returnRateByYear) {
        this.returnRateByYear = returnRateByYear;
    }

    public Double getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(Double returnRate) {
        this.returnRate = returnRate;
    }

    public Double getAnnualReturnRate() {
        return annualReturnRate;
    }

    public void setAnnualReturnRate(Double annualReturnRate) {
        this.annualReturnRate = annualReturnRate;
    }

    public List<String> getConditionPlanFlex() {
        return conditionPlanFlex;
    }

    public void setConditionPlanFlex(List<String> conditionPlanFlex) {
        this.conditionPlanFlex = conditionPlanFlex;
    }

    public Personal getPersonalData() {
        return personalData;
    }

    public void setPersonalData(Personal personalData) {
        this.personalData = personalData;
    }

    public Calculation getCalculationLimit() {
        return calculationLimit;
    }

    public void setCalculationLimit(Calculation calculationLimit) {
        this.calculationLimit = calculationLimit;
    }

    public AccountData getAccountData() {
        return accountData;
    }

    public void setAccountData(AccountData accountData) {
        this.accountData = accountData;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public ChannelSupplier getChannelSupplier() {
        return channelSupplier;
    }

    public void setChannelSupplier(ChannelSupplier channelSupplier) {
        this.channelSupplier = channelSupplier;
    }

    public CertifiedAgent getCertifiedAgent() {
        return certifiedAgent;
    }

    public void setCertifiedAgent(CertifiedAgent certifiedAgent) {
        this.certifiedAgent = certifiedAgent;
    }

    public ShopkeeperData getShopkeeperData() {
        return shopkeeperData;
    }

    public void setShopkeeperData(ShopkeeperData shopkeeperData) {
        this.shopkeeperData = shopkeeperData;
    }

}
