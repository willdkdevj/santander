/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infotera.santander.getnet.model;

import br.com.infotera.santander.getnet.model.sdk.Authentication;
import br.com.infotera.santander.getnet.model.sdk.Card;
import br.com.infotera.santander.getnet.model.sdk.Device;
import br.com.infotera.santander.getnet.model.sdk.PersonalIdentification;
import br.com.infotera.santander.getnet.model.sdk.ThreeDSOrder;

/**
 *
 * @author enioj
 */
public class ThreeDSAuthenticationRequest {

    private String customer_card_alias;
    private String override_payment_method;
    private String alternate_authentication_method;

    private Authentication authentication;
    private Device device;
    private PersonalIdentification costumer_risk_information;
    private Card card;
    private ThreeDSOrder order;

    public ThreeDSAuthenticationRequest() {
    }

    public ThreeDSAuthenticationRequest(String customer_card_alias, String override_payment_method, String alternate_authentication_method, Authentication authentication, Device device, PersonalIdentification costumer_risk_information, Card card, ThreeDSOrder order) {
        this.customer_card_alias = customer_card_alias;
        this.override_payment_method = override_payment_method;
        this.alternate_authentication_method = alternate_authentication_method;
        this.authentication = authentication;
        this.device = device;
        this.costumer_risk_information = costumer_risk_information;
        this.card = card;
        this.order = order;
    }


    public String getCustomer_card_alias() {
        return customer_card_alias;
    }

    public void setCustomer_card_alias(String customer_card_alias) {
        this.customer_card_alias = customer_card_alias;
    }

    public String getOverride_payment_method() {
        return override_payment_method;
    }

    public void setOverride_payment_method(String override_payment_method) {
        this.override_payment_method = override_payment_method;
    }

    public String getAlternate_authentication_method() {
        return alternate_authentication_method;
    }

    public void setAlternate_authentication_method(String alternate_authentication_method) {
        this.alternate_authentication_method = alternate_authentication_method;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public PersonalIdentification getCostumer_risk_information() {
        return costumer_risk_information;
    }

    public void setCostumer_risk_information(PersonalIdentification costumer_risk_information) {
        this.costumer_risk_information = costumer_risk_information;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public ThreeDSOrder getOrder() {
        return order;
    }

    public void setOrder(ThreeDSOrder order) {
        this.order = order;
    }

}
