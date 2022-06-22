/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infotera.santander.getnet.model;

import br.com.infotera.santander.getnet.model.sdk.Card;

/**
 *
 * @author enioj
 */
public class ThreeDSAuthorizationRequest {

    private String order_id;
    private Integer amount;
    private String currency;
    private String transaction_type;
    private Integer number_installments;
    private String xid;
    private String ucaf;
    private String eci;
    private String tdsdsxid;
    private String tdsver;
    private String payment_method;
    private Integer dynamic_mcc;
    private String soft_descriptor;
    private String customer_id;
    private String credentials_on_file_type;
    private Card card;

    public ThreeDSAuthorizationRequest() {
    }

    public ThreeDSAuthorizationRequest(String order_id, Integer amount, String currency, String transaction_type, Integer number_installments, String xid, String ucaf, String eci, String tdsdsxid, String tdsver, String payment_method, Integer dynamic_mcc, String soft_descriptor, String customer_id, String credentials_on_file_type, Card card) {
        this.order_id = order_id;
        this.amount = amount;
        this.currency = currency;
        this.transaction_type = transaction_type;
        this.number_installments = number_installments;
        this.xid = xid;
        this.ucaf = ucaf;
        this.eci = eci;
        this.tdsdsxid = tdsdsxid;
        this.tdsver = tdsver;
        this.payment_method = payment_method;
        this.dynamic_mcc = dynamic_mcc;
        this.soft_descriptor = soft_descriptor;
        this.customer_id = customer_id;
        this.credentials_on_file_type = credentials_on_file_type;
        this.card = card;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public Integer getNumber_installments() {
        return number_installments;
    }

    public void setNumber_installments(Integer number_installments) {
        this.number_installments = number_installments;
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public String getUcaf() {
        return ucaf;
    }

    public void setUcaf(String ucaf) {
        this.ucaf = ucaf;
    }

    public String getEci() {
        return eci;
    }

    public void setEci(String eci) {
        this.eci = eci;
    }

    public String getTdsdsxid() {
        return tdsdsxid;
    }

    public void setTdsdsxid(String tdsdsxid) {
        this.tdsdsxid = tdsdsxid;
    }

    public String getTdsver() {
        return tdsver;
    }

    public void setTdsver(String tdsver) {
        this.tdsver = tdsver;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public Integer getDynamic_mcc() {
        return dynamic_mcc;
    }

    public void setDynamic_mcc(Integer dynamic_mcc) {
        this.dynamic_mcc = dynamic_mcc;
    }

    public String getSoft_descriptor() {
        return soft_descriptor;
    }

    public void setSoft_descriptor(String soft_descriptor) {
        this.soft_descriptor = soft_descriptor;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCredentials_on_file_type() {
        return credentials_on_file_type;
    }

    public void setCredentials_on_file_type(String credentials_on_file_type) {
        this.credentials_on_file_type = credentials_on_file_type;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

}
