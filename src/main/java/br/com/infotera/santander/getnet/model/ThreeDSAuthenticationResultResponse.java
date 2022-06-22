/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infotera.santander.getnet.model;

import br.com.infotera.santander.getnet.model.sdk.Card;
import br.com.infotera.santander.getnet.model.sdk.ClientReference;
import br.com.infotera.santander.getnet.model.sdk.ConsumerAuthentication;

/**
 *
 * @author enioj
 */
public class ThreeDSAuthenticationResultResponse {

    private Card card;
    private String id;
    private String submit_time_utc;
    private String status;
    private ClientReference client_reference_information;
    private ConsumerAuthentication consumer_authentication_information;

    public ThreeDSAuthenticationResultResponse() {
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
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

}
