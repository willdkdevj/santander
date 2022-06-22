/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infotera.santander.getnet.model;

import br.com.infotera.santander.getnet.model.sdk.Amount;

/**
 *
 * @author enioj
 */
public class CaptureRequest {

    private Amount amount;
    private String payment_id;

    public CaptureRequest() {
    }

    public CaptureRequest(Amount amount, String payment_id) {
        this.amount = amount;
        this.payment_id = payment_id;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

}
