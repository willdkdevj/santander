/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.model;

/**
 *
 * @author William Dias
 */
public class Tariff {
    
    private String description; // "45922",
    private Integer value; // 400,
    private Double installmentValue; // 21.76,
    private Double fullInstallmentValue; // 599.69,
    private Double fullInstallmentValueWithoutTariffInstallmentValue; // 577.93,
    private String paymentType; // "IN_INSTALLMENTS"

    public Tariff() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Double getInstallmentValue() {
        return installmentValue;
    }

    public void setInstallmentValue(Double installmentValue) {
        this.installmentValue = installmentValue;
    }

    public Double getFullInstallmentValue() {
        return fullInstallmentValue;
    }

    public void setFullInstallmentValue(Double fullInstallmentValue) {
        this.fullInstallmentValue = fullInstallmentValue;
    }

    public Double getFullInstallmentValueWithoutTariffInstallmentValue() {
        return fullInstallmentValueWithoutTariffInstallmentValue;
    }

    public void setFullInstallmentValueWithoutTariffInstallmentValue(Double fullInstallmentValueWithoutTariffInstallmentValue) {
        this.fullInstallmentValueWithoutTariffInstallmentValue = fullInstallmentValueWithoutTariffInstallmentValue;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    
}
