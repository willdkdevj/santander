package br.com.infotera.santander.financiamento.rqrs;

import br.com.infotera.santander.financiamento.model.Cashback;
import br.com.infotera.santander.financiamento.model.Form;
import br.com.infotera.santander.financiamento.model.Installment;
import br.com.infotera.santander.financiamento.model.InstallmentMethodDate;
import br.com.infotera.santander.financiamento.model.PaymentMethod;
import br.com.infotera.santander.financiamento.model.ShopkeeperData;
import br.com.infotera.santander.financiamento.model.Simulation;
import br.com.infotera.santander.financiamento.model.Tariff;
import java.util.List;

public class SimulacaoRS {
    // Simulation [POST]/simulation/simulation
    private Double calcContractFee; // 1.99,
    private Double annualInternalReturnRate; // 44.9,
    private Double monthlyInternalReturnRate; // 3.1,
    private Double annualCalcContractFee; // 26.68,
    private Double financedValue; // 10000,
    private Double totalAmountFinanced; // 11340.99,
    private Double totalAmountPaid; // 14392.56,
    private String firstInstallmentDueDate; // "2022-03-23",
    private Integer installmentAmount; // 24,
    private Double installmentValue; // 599.69,
    private Double entryValue; // 0,
    private Integer minInstallmentAmount; // 3,
    private Integer maxInstallmentAmount; // 36,
    private Double minFinancedValue; // 500,
    private Double maxFinancedValue; // 100000,
    private String saleOffTypeCode; // "PHYSICAL",
    private Tariff tariff;
    private String paymentPlan; // "FLT",
    private String registrationFeeType; // "IN_INSTALLMENTS",
    private Boolean isAllowedFlexInstallments; // false,
    private Double maxAllowedFlexValue; // 1000,
    private Integer maxAllowedFlexInstallmentsNumber; // 3,
    private Integer maxAllowedInstallmentValue; // 100000,
    private Integer minAllowedInstallmentValue; // 1,
    private String liquidationTypeCode; //"B",
    private String minAllowedFirstInstallmentDueDate; // "2022-03-03",
    private String maxAllowedFirstInstallmentDueDate; // "2022-03-23",
    private Boolean isValidFirstInstallmentDueDate; // true,
    private Boolean isValidSubmitButton; // true,
    private List<Form> tax;
    private List<Form> insurance;
    private List<PaymentMethod> paymentMethods;
    private List<Installment> installments;
    private List<String> flexInstallments;
    private Cashback cashback;
    private ShopkeeperData shopkeeperData;
    private Integer flexInstallmentsNumber;
    private List<InstallmentMethodDate> installmentPMethodsDate;
    private String proposalId;
    
    // ProposalID [GET]/simulation/{proposalId}
    private Simulation simulationParameters;
    private Simulation simulationData;
    
    public SimulacaoRS() {
    }

    public Double getCalcContractFee() {
        return calcContractFee;
    }

    public void setCalcContractFee(Double calcContractFee) {
        this.calcContractFee = calcContractFee;
    }

    public Double getAnnualInternalReturnRate() {
        return annualInternalReturnRate;
    }

    public void setAnnualInternalReturnRate(Double annualInternalReturnRate) {
        this.annualInternalReturnRate = annualInternalReturnRate;
    }

    public Double getMonthlyInternalReturnRate() {
        return monthlyInternalReturnRate;
    }

    public void setMonthlyInternalReturnRate(Double monthlyInternalReturnRate) {
        this.monthlyInternalReturnRate = monthlyInternalReturnRate;
    }

    public Double getAnnualCalcContractFee() {
        return annualCalcContractFee;
    }

    public void setAnnualCalcContractFee(Double annualCalcContractFee) {
        this.annualCalcContractFee = annualCalcContractFee;
    }

    public Double getFinancedValue() {
        return financedValue;
    }

    public void setFinancedValue(Double financedValue) {
        this.financedValue = financedValue;
    }

    public Double getTotalAmountFinanced() {
        return totalAmountFinanced;
    }

    public void setTotalAmountFinanced(Double totalAmountFinanced) {
        this.totalAmountFinanced = totalAmountFinanced;
    }

    public Double getTotalAmountPaid() {
        return totalAmountPaid;
    }

    public void setTotalAmountPaid(Double totalAmountPaid) {
        this.totalAmountPaid = totalAmountPaid;
    }

    public String getFirstInstallmentDueDate() {
        return firstInstallmentDueDate;
    }

    public void setFirstInstallmentDueDate(String firstInstallmentDueDate) {
        this.firstInstallmentDueDate = firstInstallmentDueDate;
    }

    public Integer getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(Integer installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public Double getInstallmentValue() {
        return installmentValue;
    }

    public void setInstallmentValue(Double installmentValue) {
        this.installmentValue = installmentValue;
    }

    public Double getEntryValue() {
        return entryValue;
    }

    public void setEntryValue(Double entryValue) {
        this.entryValue = entryValue;
    }

    public Integer getMinInstallmentAmount() {
        return minInstallmentAmount;
    }

    public void setMinInstallmentAmount(Integer minInstallmentAmount) {
        this.minInstallmentAmount = minInstallmentAmount;
    }

    public Integer getMaxInstallmentAmount() {
        return maxInstallmentAmount;
    }

    public void setMaxInstallmentAmount(Integer maxInstallmentAmount) {
        this.maxInstallmentAmount = maxInstallmentAmount;
    }

    public Double getMinFinancedValue() {
        return minFinancedValue;
    }

    public void setMinFinancedValue(Double minFinancedValue) {
        this.minFinancedValue = minFinancedValue;
    }

    public Double getMaxFinancedValue() {
        return maxFinancedValue;
    }

    public void setMaxFinancedValue(Double maxFinancedValue) {
        this.maxFinancedValue = maxFinancedValue;
    }

    public String getSaleOffTypeCode() {
        return saleOffTypeCode;
    }

    public void setSaleOffTypeCode(String saleOffTypeCode) {
        this.saleOffTypeCode = saleOffTypeCode;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public String getPaymentPlan() {
        return paymentPlan;
    }

    public void setPaymentPlan(String paymentPlan) {
        this.paymentPlan = paymentPlan;
    }

    public String getRegistrationFeeType() {
        return registrationFeeType;
    }

    public void setRegistrationFeeType(String registrationFeeType) {
        this.registrationFeeType = registrationFeeType;
    }

    public Boolean getIsAllowedFlexInstallments() {
        return isAllowedFlexInstallments;
    }

    public void setIsAllowedFlexInstallments(Boolean isAllowedFlexInstallments) {
        this.isAllowedFlexInstallments = isAllowedFlexInstallments;
    }

    public Double getMaxAllowedFlexValue() {
        return maxAllowedFlexValue;
    }

    public void setMaxAllowedFlexValue(Double maxAllowedFlexValue) {
        this.maxAllowedFlexValue = maxAllowedFlexValue;
    }

    public Integer getMaxAllowedFlexInstallmentsNumber() {
        return maxAllowedFlexInstallmentsNumber;
    }

    public void setMaxAllowedFlexInstallmentsNumber(Integer maxAllowedFlexInstallmentsNumber) {
        this.maxAllowedFlexInstallmentsNumber = maxAllowedFlexInstallmentsNumber;
    }

    public Integer getMaxAllowedInstallmentValue() {
        return maxAllowedInstallmentValue;
    }

    public void setMaxAllowedInstallmentValue(Integer maxAllowedInstallmentValue) {
        this.maxAllowedInstallmentValue = maxAllowedInstallmentValue;
    }

    public Integer getMinAllowedInstallmentValue() {
        return minAllowedInstallmentValue;
    }

    public void setMinAllowedInstallmentValue(Integer minAllowedInstallmentValue) {
        this.minAllowedInstallmentValue = minAllowedInstallmentValue;
    }

    public String getLiquidationTypeCode() {
        return liquidationTypeCode;
    }

    public void setLiquidationTypeCode(String liquidationTypeCode) {
        this.liquidationTypeCode = liquidationTypeCode;
    }

    public String getMinAllowedFirstInstallmentDueDate() {
        return minAllowedFirstInstallmentDueDate;
    }

    public void setMinAllowedFirstInstallmentDueDate(String minAllowedFirstInstallmentDueDate) {
        this.minAllowedFirstInstallmentDueDate = minAllowedFirstInstallmentDueDate;
    }

    public String getMaxAllowedFirstInstallmentDueDate() {
        return maxAllowedFirstInstallmentDueDate;
    }

    public void setMaxAllowedFirstInstallmentDueDate(String maxAllowedFirstInstallmentDueDate) {
        this.maxAllowedFirstInstallmentDueDate = maxAllowedFirstInstallmentDueDate;
    }

    public Boolean getIsValidFirstInstallmentDueDate() {
        return isValidFirstInstallmentDueDate;
    }

    public void setIsValidFirstInstallmentDueDate(Boolean isValidFirstInstallmentDueDate) {
        this.isValidFirstInstallmentDueDate = isValidFirstInstallmentDueDate;
    }

    public Boolean getIsValidSubmitButton() {
        return isValidSubmitButton;
    }

    public void setIsValidSubmitButton(Boolean isValidSubmitButton) {
        this.isValidSubmitButton = isValidSubmitButton;
    }

    public List<Form> getTax() {
        return tax;
    }

    public void setTax(List<Form> tax) {
        this.tax = tax;
    }

    public List<Form> getInsurance() {
        return insurance;
    }

    public void setInsurance(List<Form> insurance) {
        this.insurance = insurance;
    }

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public List<Installment> getInstallments() {
        return installments;
    }

    public void setInstallments(List<Installment> installments) {
        this.installments = installments;
    }

    public List<String> getFlexInstallments() {
        return flexInstallments;
    }

    public void setFlexInstallments(List<String> flexInstallments) {
        this.flexInstallments = flexInstallments;
    }

    public Cashback getCashback() {
        return cashback;
    }

    public void setCashback(Cashback cashback) {
        this.cashback = cashback;
    }

    public ShopkeeperData getShopkeeperData() {
        return shopkeeperData;
    }

    public void setShopkeeperData(ShopkeeperData shopkeeperData) {
        this.shopkeeperData = shopkeeperData;
    }

    public Integer getFlexInstallmentsNumber() {
        return flexInstallmentsNumber;
    }

    public void setFlexInstallmentsNumber(Integer flexInstallmentsNumber) {
        this.flexInstallmentsNumber = flexInstallmentsNumber;
    }

    public List<InstallmentMethodDate> getInstallmentPMethodsDate() {
        return installmentPMethodsDate;
    }

    public void setInstallmentPMethodsDate(List<InstallmentMethodDate> installmentPMethodsDate) {
        this.installmentPMethodsDate = installmentPMethodsDate;
    }

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public Simulation getSimulationParameters() {
        return simulationParameters;
    }

    public void setSimulationParameters(Simulation simulationParameters) {
        this.simulationParameters = simulationParameters;
    }

    public Simulation getSimulationData() {
        return simulationData;
    }

    public void setSimulationData(Simulation simulationData) {
        this.simulationData = simulationData;
    }

}
