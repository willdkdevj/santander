/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.model;

/**
 *
 * @author William Dias
 */
public class Form {
    
    private Integer id;
    private String description;
    private Double value;
    private String name;
    private Double installmentValue;
    private Double fullInstallmentValue;
    private Double fullInstallmentValueWithoutInsuranceInstallmentValue;
    private String code;
    private String type;
    private String insertDateTime;
    private String updateDateTime;
    private String updateUserCode;
    
    public Form() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Double getFullInstallmentValueWithoutInsuranceInstallmentValue() {
        return fullInstallmentValueWithoutInsuranceInstallmentValue;
    }

    public void setFullInstallmentValueWithoutInsuranceInstallmentValue(Double fullInstallmentValueWithoutInsuranceInstallmentValue) {
        this.fullInstallmentValueWithoutInsuranceInstallmentValue = fullInstallmentValueWithoutInsuranceInstallmentValue;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInsertDateTime() {
        return insertDateTime;
    }

    public void setInsertDateTime(String insertDateTime) {
        this.insertDateTime = insertDateTime;
    }

    public String getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public String getUpdateUserCode() {
        return updateUserCode;
    }

    public void setUpdateUserCode(String updateUserCode) {
        this.updateUserCode = updateUserCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
