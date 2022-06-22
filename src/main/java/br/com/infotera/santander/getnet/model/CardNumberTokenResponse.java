/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infotera.santander.getnet.model;

/**
 *
 * @author enioj
 */
public class CardNumberTokenResponse {

    private String number_token;

    public CardNumberTokenResponse() {
    }

    public CardNumberTokenResponse(String number_token) {
        this.number_token = number_token;
    }

    public String getNumber_token() {
        return number_token;
    }

    public void setNumber_token(String number_token) {
        this.number_token = number_token;
    }
}
