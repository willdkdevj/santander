/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infotera.santander.getnet.model.sdk;

/**
 *
 * @author danilo
 */
public class Card {

    private String number_token, cardholder_name, security_code, brand, expiration_month, expiration_year;
    private String type_card;

    public Card() {
    }

    public Card(String number_token, String cardholder_name, String security_code, String brand, String expiration_month, String expiration_year) {
        this.number_token = number_token;
        this.cardholder_name = cardholder_name;
        this.security_code = security_code;
        this.brand = brand;
        this.expiration_month = expiration_month;
        this.expiration_year = expiration_year;
    }

    public Card(String number_token, String expiration_month, String expiration_year, String type_card) {
        this.number_token = number_token;
        this.expiration_month = expiration_month;
        this.expiration_year = expiration_year;
        this.type_card = type_card;
    }

    public String getNumber_token() {
        return number_token;
    }

    public void setNumber_token(String number_token) {
        this.number_token = number_token;
    }

    public String getCardholder_name() {
        return cardholder_name;
    }

    public void setCardholder_name(String cardholder_name) {
        this.cardholder_name = cardholder_name;
    }

    public String getSecurity_code() {
        return security_code;
    }

    public void setSecurity_code(String security_code) {
        this.security_code = security_code;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getExpiration_month() {
        return expiration_month;
    }

    public void setExpiration_month(String expiration_month) {
        this.expiration_month = expiration_month;
    }

    public String getExpiration_year() {
        return expiration_year;
    }

    public void setExpiration_year(String expiration_year) {
        this.expiration_year = expiration_year;
    }

    public String getType_card() {
        return type_card;
    }

    public void setType_card(String type_card) {
        this.type_card = type_card;
    }

}
