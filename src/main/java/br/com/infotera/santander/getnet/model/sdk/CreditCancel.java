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
public class CreditCancel {

    private String canceled_at, message;

    public CreditCancel() {
    }

    public CreditCancel(String canceled_at, String message) {
        this.canceled_at = canceled_at;
        this.message = message;
    }

    public String getCanceled_at() {
        return canceled_at;
    }

    public void setCanceled_at(String canceled_at) {
        this.canceled_at = canceled_at;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
