/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.financiamento.model;

/**
 *
 * @author William Dias
 */
class Phone {
    
    private String id;
    private String clientId;
    private Boolean mainFlag;
    private String type;
    private String ddiNumber;
    private String dddNumber;
    private String number;

    public Phone() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getMainFlag() {
        return mainFlag;
    }

    public void setMainFlag(Boolean mainFlag) {
        this.mainFlag = mainFlag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDdiNumber() {
        return ddiNumber;
    }

    public void setDdiNumber(String ddiNumber) {
        this.ddiNumber = ddiNumber;
    }

    public String getDddNumber() {
        return dddNumber;
    }

    public void setDddNumber(String dddNumber) {
        this.dddNumber = dddNumber;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
}
