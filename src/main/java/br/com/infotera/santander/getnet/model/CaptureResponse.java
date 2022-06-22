/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infotera.santander.getnet.model;

import br.com.infotera.santander.getnet.model.sdk.CreditConfirm;

/**
 *
 * @author enioj
 */
public class CaptureResponse {

    private String payment_id, seller_id;
    private int amount;
    private String currency, order_id, status;
    private CreditConfirm credit_confirm;
    private String reason_code;
    private String reason_message;

    public CaptureResponse() {
    }

    public CaptureResponse(String payment_id, String seller_id, int amount, String currency, String order_id, String status, CreditConfirm credit_confirm) {
        this.payment_id = payment_id;
        this.seller_id = seller_id;
        this.amount = amount;
        this.currency = currency;
        this.order_id = order_id;
        this.status = status;
        this.credit_confirm = credit_confirm;
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

    public CreditConfirm getCredit_confirm() {
        return credit_confirm;
    }

    public void setCredit_confirm(CreditConfirm credit_confirm) {
        this.credit_confirm = credit_confirm;
    }

    public String getReason_code() {
        return reason_code;
    }

    public void setReason_code(String reason_code) {
        this.reason_code = reason_code;
    }

    public String getReason_message() {
        return reason_message;
    }

    public void setReason_message(String reason_message) {
        this.reason_message = reason_message;
    }

}
