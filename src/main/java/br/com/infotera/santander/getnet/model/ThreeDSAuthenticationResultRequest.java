/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infotera.santander.getnet.model;

import br.com.infotera.santander.getnet.model.sdk.Card;

/**
 *
 * @author enioj
 */
public class ThreeDSAuthenticationResultRequest {

    private String currency;
    private String override_payment_method;
    private String token;
    private String token_challenge;
    private Integer total_amount;
    private Card card;

    public ThreeDSAuthenticationResultRequest() {
    }

    public ThreeDSAuthenticationResultRequest(String currency, String override_payment_method, String token, String token_challenge, Integer total_amount, Card card) {
        this.currency = currency;
        this.override_payment_method = override_payment_method;
        this.token = token;
        this.token_challenge = token_challenge;
        this.total_amount = total_amount;
        this.card = card;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOverride_payment_method() {
        return override_payment_method;
    }

    public void setOverride_payment_method(String override_payment_method) {
        this.override_payment_method = override_payment_method;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken_challenge() {
        return token_challenge;
    }

    public void setToken_challenge(String token_challenge) {
        this.token_challenge = token_challenge;
    }

    public Integer getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Integer total_amount) {
        this.total_amount = total_amount;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

}
