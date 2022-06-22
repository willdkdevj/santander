/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infotera.santander.getnet.model;

import br.com.infotera.santander.getnet.model.sdk.Card;
import br.com.infotera.santander.getnet.model.sdk.ClientReference;
import br.com.infotera.santander.getnet.model.sdk.ConsumerAuthentication;
import br.com.infotera.santander.getnet.model.sdk.ErrorInformation;

/**
 *
 * @author enioj
 */
public class ThreeDSAuthenticationResponse {
    
    private String id;
    private String submit_time_utc;
    private String status;
    private String reference_id;
    private String org_unit_id;
//    private String send_ds_date;
//    private String rec_ds_date;

    private Card card;
    private ClientReference client_reference_information;
    private ConsumerAuthentication consumer_authentication_information;
    private ErrorInformation error_information;

    public ThreeDSAuthenticationResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubmit_time_utc() {
        return submit_time_utc;
    }

    public void setSubmit_time_utc(String submit_time_utc) {
        this.submit_time_utc = submit_time_utc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReference_id() {
        return reference_id;
    }

    public void setReference_id(String reference_id) {
        this.reference_id = reference_id;
    }

    public String getOrg_unit_id() {
        return org_unit_id;
    }

    public void setOrg_unit_id(String org_unit_id) {
        this.org_unit_id = org_unit_id;
    }

//    public Integer getSend_ds_date() {
//        return send_ds_date;
//    }
//
//    public void setSend_ds_date(Integer send_ds_date) {
//        this.send_ds_date = send_ds_date;
//    }
//
//    public Integer getRec_ds_date() {
//        return rec_ds_date;
//    }
//
//    public void setRec_ds_date(Integer rec_ds_date) {
//        this.rec_ds_date = rec_ds_date;
//    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public ClientReference getClient_reference_information() {
        return client_reference_information;
    }

    public void setClient_reference_information(ClientReference client_reference_information) {
        this.client_reference_information = client_reference_information;
    }

    public ConsumerAuthentication getConsumer_authentication_information() {
        return consumer_authentication_information;
    }

    public void setConsumer_authentication_information(ConsumerAuthentication consumer_authentication_information) {
        this.consumer_authentication_information = consumer_authentication_information;
    }

    public ErrorInformation getError_information() {
        return error_information;
    }

    public void setError_information(ErrorInformation error_information) {
        this.error_information = error_information;
    }

}
