/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infotera.santander.getnet.model;

import br.com.infotera.santander.getnet.model.sdk.CreditCancel;

/**
 *
 * @author enioj
 */
public class CancelResponse {

    private String payment_id;
    private String seller_id;
    private int amount;
    private String currency;
    private String order_id;
    private String cancel_request_at;
    private String cancel_request_id;
    private String cancel_custom_key;
    private String status;
    private CreditCancel credit_cancel;

    public CancelResponse() {
    }

    public CancelResponse(String payment_id, String seller_id, int amount, String currency, String order_id, String status, CreditCancel credit_cancel) {
        this.payment_id = payment_id;
        this.seller_id = seller_id;
        this.amount = amount;
        this.currency = currency;
        this.order_id = order_id;
        this.status = status;
        this.credit_cancel = credit_cancel;
    }

    public CancelResponse(String payment_id, String seller_id, String cancel_request_at, String cancel_request_id, String cancel_custom_key, String status) {
        this.payment_id = payment_id;
        this.seller_id = seller_id;
        this.cancel_request_at = cancel_request_at;
        this.cancel_request_id = cancel_request_id;
        this.cancel_custom_key = cancel_custom_key;
        this.status = status;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public CreditCancel getCredit_cancel() {
        return credit_cancel;
    }

    public void setCredit_cancel(CreditCancel credit_cancel) {
        this.credit_cancel = credit_cancel;
    }

    public String getCancel_request_at() {
        return cancel_request_at;
    }

    public void setCancel_request_at(String cancel_request_at) {
        this.cancel_request_at = cancel_request_at;
    }

    public String getCancel_request_id() {
        return cancel_request_id;
    }

    public void setCancel_request_id(String cancel_request_id) {
        this.cancel_request_id = cancel_request_id;
    }

    public String getCancel_custom_key() {
        return cancel_custom_key;
    }

    public void setCancel_custom_key(String cancel_custom_key) {
        this.cancel_custom_key = cancel_custom_key;
    }
}
