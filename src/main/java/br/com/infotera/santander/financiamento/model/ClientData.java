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
public class ClientData {
    
    private String id;
    private String productCode;
    private String liquidationTypeCode;
    private String creationDate;
    private String expirationDate;
    private Boolean isTravelSubSegment;
    private String storeCode;
    private List<Client> clients;
    private List<Object> travelers;
    private Information additionalInformation;
    private List<Flag> flags;

    public ClientData() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getLiquidationTypeCode() {
        return liquidationTypeCode;
    }

    public void setLiquidationTypeCode(String liquidationTypeCode) {
        this.liquidationTypeCode = liquidationTypeCode;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Boolean getIsTravelSubSegment() {
        return isTravelSubSegment;
    }

    public void setIsTravelSubSegment(Boolean isTravelSubSegment) {
        this.isTravelSubSegment = isTravelSubSegment;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Object> getTravelers() {
        return travelers;
    }

    public void setTravelers(List<Object> travelers) {
        this.travelers = travelers;
    }

    public Information getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(Information additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public List<Flag> getFlags() {
        return flags;
    }

    public void setFlags(List<Flag> flags) {
        this.flags = flags;
    }
    
}
