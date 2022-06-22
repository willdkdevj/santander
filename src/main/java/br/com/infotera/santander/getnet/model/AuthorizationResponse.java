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
public class AuthorizationResponse {

    private String payment_id, seller_id;
    private int amount;
    private String currency, order_id, status, received_at;
    private CreditResponse credit;

    public AuthorizationResponse() {
    }

    public AuthorizationResponse(String payment_id, String seller_id, int amount, String currency, String order_id, String status, String received_at, CreditResponse credit) {
        this.payment_id = payment_id;
        this.seller_id = seller_id;
        this.amount = amount;
        this.currency = currency;
        this.order_id = order_id;
        this.status = status;
        this.received_at = received_at;
        this.credit = credit;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReceived_at() {
        return received_at;
    }

    public void setReceived_at(String received_at) {
        this.received_at = received_at;
    }

    public CreditResponse getCredit() {
        return credit;
    }

    public void setCredit(CreditResponse credit) {
        this.credit = credit;
    }
}
