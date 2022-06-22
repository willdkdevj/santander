/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infotera.santander.getnet.model.sdk;

/**
 *
 * @author enioj
 */
public class Cancel {

    private String payment_id;
    private Integer cancel_amount;
    private String cancel_custom_key;
    private String payment_method;

    public Cancel() {
    }

    public Cancel(String payment_id, Integer cancel_amount, String cancel_custom_key) {
        this.payment_id = payment_id;
        this.cancel_amount = cancel_amount;
        this.cancel_custom_key = cancel_custom_key;
    }

    public Cancel(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public Integer getCancel_amount() {
        return cancel_amount;
    }

    public void setCancel_amount(Integer cancel_amount) {
        this.cancel_amount = cancel_amount;
    }

    public String getCancel_custom_key() {
        return cancel_custom_key;
    }

    public void setCancel_custom_key(String cancel_custom_key) {
        this.cancel_custom_key = cancel_custom_key;
    }

}
