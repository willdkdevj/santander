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
public class CardNumberTokenRequest {

    private String card_number, customer_id;

    public CardNumberTokenRequest() {

    }

    public CardNumberTokenRequest(String card_number) {
        this.card_number = card_number;
    }

    public CardNumberTokenRequest(String card_number, String customer_id) {
        this.card_number = card_number;
        this.customer_id = customer_id;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }
}
