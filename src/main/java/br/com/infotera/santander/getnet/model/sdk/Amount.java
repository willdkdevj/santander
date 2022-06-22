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
public class Amount {

    private int amount;
    private String payment_method;

    public Amount() {
    }

    public Amount(int amount) {
        this.amount = amount;
    }

    public Amount(int amount, String payment_method) {
        this.amount = amount;
        this.payment_method = payment_method;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

}
