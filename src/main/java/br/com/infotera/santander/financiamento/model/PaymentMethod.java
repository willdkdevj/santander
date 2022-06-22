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
public class PaymentMethod {
    
    private String code; // "FLT",
    private Boolean hasFlexInstallments; // false,
    private String description; // "Tradicional",
    private String firstInstallmentPMethodsDate; // "2022-02-01",
    private Double installmentDefaultValue; // 599.69,
    private Integer installmentPaymentMethodsValue; // 0,
    private Double firstInstallmentValue; // 599.69,
    private Double lastInstallmentValue; // 599.69,
    private Integer installmentDefaultAmount; // 24,
    private Integer installmentPaymentMethodsAmount; // 0,
    private List<Range> monthsRange;

    public PaymentMethod() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getHasFlexInstallments() {
        return hasFlexInstallments;
    }

    public void setHasFlexInstallments(Boolean hasFlexInstallments) {
        this.hasFlexInstallments = hasFlexInstallments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstInstallmentPMethodsDate() {
        return firstInstallmentPMethodsDate;
    }

    public void setFirstInstallmentPMethodsDate(String firstInstallmentPMethodsDate) {
        this.firstInstallmentPMethodsDate = firstInstallmentPMethodsDate;
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

    public List<Range> getMonthsRange() {
        return monthsRange;
    }

    public void setMonthsRange(List<Range> monthsRange) {
        this.monthsRange = monthsRange;
    }
    
}
