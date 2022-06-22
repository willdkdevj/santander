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
public class Credit {

    private boolean delayed, authenticated, pre_authorization, save_card_data;
    private String transaction_type;
    private int number_installments;
    private String soft_descriptor;
    private int dynamic_mcc;
    private Card card;

    public Credit() {
    }

    public Credit(boolean delayed, boolean authenticated, boolean pre_authorization, boolean save_card_data, String transaction_type, int number_installments, String soft_descriptor, int dynamic_mcc, Card card) {
        this.delayed = delayed;
        this.authenticated = authenticated;
        this.pre_authorization = pre_authorization;
        this.save_card_data = save_card_data;
        this.transaction_type = transaction_type;
        this.number_installments = number_installments;
        this.soft_descriptor = soft_descriptor;
        this.dynamic_mcc = dynamic_mcc;
        this.card = card;
    }

    public boolean isDelayed() {
        return delayed;
    }

    public void setDelayed(boolean delayed) {
        this.delayed = delayed;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public boolean isPre_authorization() {
        return pre_authorization;
    }

    public void setPre_authorization(boolean pre_authorization) {
        this.pre_authorization = pre_authorization;
    }

    public boolean isSave_card_data() {
        return save_card_data;
    }

    public void setSave_card_data(boolean save_card_data) {
        this.save_card_data = save_card_data;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public int getNumber_installments() {
        return number_installments;
    }

    public void setNumber_installments(int number_installments) {
        this.number_installments = number_installments;
    }

    public String getSoft_descriptor() {
        return soft_descriptor;
    }

    public void setSoft_descriptor(String soft_descriptor) {
        this.soft_descriptor = soft_descriptor;
    }

    public int getDynamic_mcc() {
        return dynamic_mcc;
    }

    public void setDynamic_mcc(int dynamic_mcc) {
        this.dynamic_mcc = dynamic_mcc;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

}
