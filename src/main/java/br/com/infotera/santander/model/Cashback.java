/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.model;

/**
 *
 * @author William Dias
 */
public class Cashback {
    
    private String elegibleType;
    private Double cashbackAmount;

    public Cashback() {
    }

    public String getElegibleType() {
        return elegibleType;
    }

    public void setElegibleType(String elegibleType) {
        this.elegibleType = elegibleType;
    }

    public Double getCashbackAmount() {
        return cashbackAmount;
    }

    public void setCashbackAmount(Double cashbackAmount) {
        this.cashbackAmount = cashbackAmount;
    }
    
}
