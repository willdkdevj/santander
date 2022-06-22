/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infotera.santander.getnet.model;

/**
 *
 * @author enioj
 */
public class CreditResponse {

    private boolean delayed;
    private String authorization_code, authorized_at;
    private int reason_code;
    private String reason_message, acquirer, soft_descriptor, brand, terminal_nsu;
    private String acquirer_transaction_id;
    private String transaction_id;

    public CreditResponse() {
    }

    public CreditResponse(boolean delayed, String authorization_code, String authorized_at, int reason_code, String reason_message, String acquirer, String soft_descriptor, String brand, String terminal_nsu, String acquirer_transaction_id, String transaction_id) {
        this.delayed = delayed;
        this.authorization_code = authorization_code;
        this.authorized_at = authorized_at;
        this.reason_code = reason_code;
        this.reason_message = reason_message;
        this.acquirer = acquirer;
        this.soft_descriptor = soft_descriptor;
        this.brand = brand;
        this.terminal_nsu = terminal_nsu;
        this.acquirer_transaction_id = acquirer_transaction_id;
        this.transaction_id = transaction_id;
    }

    public boolean isDelayed() {
        return delayed;
    }

    public void setDelayed(boolean delayed) {
        this.delayed = delayed;
    }

    public String getAuthorization_code() {
        return authorization_code;
    }

    public void setAuthorization_code(String authorization_code) {
        this.authorization_code = authorization_code;
    }

    public String getAuthorized_at() {
        return authorized_at;
    }

    public void setAuthorized_at(String authorized_at) {
        this.authorized_at = authorized_at;
    }

    public int getReason_code() {
        return reason_code;
    }

    public void setReason_code(int reason_code) {
        this.reason_code = reason_code;
    }

    public String getReason_message() {
        return reason_message;
    }

    public void setReason_message(String reason_message) {
        this.reason_message = reason_message;
    }

    public String getAcquirer() {
        return acquirer;
    }

    public void setAcquirer(String acquirer) {
        this.acquirer = acquirer;
    }

    public String getSoft_descriptor() {
        return soft_descriptor;
    }

    public void setSoft_descriptor(String soft_descriptor) {
        this.soft_descriptor = soft_descriptor;
    }

    public String getTerminal_nsu() {
        return terminal_nsu;
    }

    public void setTerminal_nsu(String terminal_nsu) {
        this.terminal_nsu = terminal_nsu;
    }

    public String getAcquirer_transaction_id() {
        return acquirer_transaction_id;
    }

    public void setAcquirer_transaction_id(String acquirer_transaction_id) {
        this.acquirer_transaction_id = acquirer_transaction_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

}
