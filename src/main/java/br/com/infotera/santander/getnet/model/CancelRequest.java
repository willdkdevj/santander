/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infotera.santander.getnet.model;

import br.com.infotera.santander.getnet.model.sdk.Cancel;

/**
 *
 * @author enioj
 */
public class CancelRequest {

    private String payment_id;
    private Cancel cancel;
    private boolean outroDia;

    public CancelRequest() {
    }

    public CancelRequest(Cancel cancel, boolean outroDia) {
        this.cancel = cancel;
        this.outroDia = outroDia;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public Cancel getCancel() {
        return cancel;
    }

    public void setCancel(Cancel cancel) {
        this.cancel = cancel;
    }

    public boolean isOutroDia() {
        return outroDia;
    }

    public void setOutroDia(boolean outroDia) {
        this.outroDia = outroDia;
    }

}
