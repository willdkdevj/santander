/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.model;

/**
 *
 * @author William Dias
 */
public class PlanTypeSchema {
    
    private String planTypeCode; // "FLT",
    private String planTypeDescription; // "Tradicional",
    private Boolean hasFlexInstallments; // false,
    private Double installmentDefaultValue; // 510.97,
    private Integer installmentPaymentMethodsValue; // 0,
    private Double firstInstallmentValue; // 510.97,
    private Double lastInstallmentValue; // 510.97,
    private Integer installmentDefaultAmount; // 24,
    private Integer installmentPaymentMethodsAmount;

    public PlanTypeSchema() {
    }

    public String getPlanTypeCode() {
        return planTypeCode;
    }

    public void setPlanTypeCode(String planTypeCode) {
        this.planTypeCode = planTypeCode;
    }

    public String getPlanTypeDescription() {
        return planTypeDescription;
    }

    public void setPlanTypeDescription(String planTypeDescription) {
        this.planTypeDescription = planTypeDescription;
    }

    public Boolean getHasFlexInstallments() {
        return hasFlexInstallments;
    }

    public void setHasFlexInstallments(Boolean hasFlexInstallments) {
        this.hasFlexInstallments = hasFlexInstallments;
    }

    public Double getInstallmentDefaultValue() {
        return installmentDefaultValue;
    }

    public void setInstallmentDefaultValue(Double installmentDefaultValue) {
        this.installmentDefaultValue = installmentDefaultValue;
    }

    public Integer getInstallmentPaymentMethodsValue() {
        return installmentPaymentMethodsValue;
    }

    public void setInstallmentPaymentMethodsValue(Integer installmentPaymentMethodsValue) {
        this.installmentPaymentMethodsValue = installmentPaymentMethodsValue;
    }

    public Double getFirstInstallmentValue() {
        return firstInstallmentValue;
    }

    public void setFirstInstallmentValue(Double firstInstallmentValue) {
        this.firstInstallmentValue = firstInstallmentValue;
    }

    public Double getLastInstallmentValue() {
        return lastInstallmentValue;
    }

    public void setLastInstallmentValue(Double lastInstallmentValue) {
        this.lastInstallmentValue = lastInstallmentValue;
    }

    public Integer getInstallmentDefaultAmount() {
        return installmentDefaultAmount;
    }

    public void setInstallmentDefaultAmount(Integer installmentDefaultAmount) {
        this.installmentDefaultAmount = installmentDefaultAmount;
    }

    public Integer getInstallmentPaymentMethodsAmount() {
        return installmentPaymentMethodsAmount;
    }

    public void setInstallmentPaymentMethodsAmount(Integer installmentPaymentMethodsAmount) {
        this.installmentPaymentMethodsAmount = installmentPaymentMethodsAmount;
    }
    
}
