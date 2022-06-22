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
public class Terms {
    
    private String id;
    private String code;
    private String description;
    private String domainCode;
    private List<Terms> months;
    
    public Terms() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Terms> getMonths() {
        return months;
    }

    public void setMonths(List<Terms> months) {
        this.months = months;
    }

    public String getDomainCode() {
        return domainCode;
    }

    public void setDomainCode(String domainCode) {
        this.domainCode = domainCode;
    }
    
}
