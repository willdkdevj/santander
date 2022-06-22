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
public class ThreeDSAuthorizationResponse {

    private String payment_id;
    private String seller_id;
    private Integer amount;
    private String currency;
    private String order_id;
    private String status;
    private String payment_method;
    private String received_at;
    private String transaction_id;
    private String original_transaction_id;
    private String authorized_at;
    private Integer reason_code;
    private String reason_message;
    private String acquirer;
    private String soft_descriptor;
    private String brand;
    private String authorization_code;
    private String acquirer_transaction_id;
    private String eci;
    private String ucaf;
    private String xid;
    private Integer total_installment_amount;
    private Integer first_installment_amount;
    private Integer other_installment_amount;

    public ThreeDSAuthorizationResponse() {
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
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

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getReceived_at() {
        return received_at;
    }

    public void setReceived_at(String received_at) {
        this.received_at = received_at;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOriginal_transaction_id() {
        return original_transaction_id;
    }

    public void setOriginal_transaction_id(String original_transaction_id) {
        this.original_transaction_id = original_transaction_id;
    }

    public String getAuthorized_at() {
        return authorized_at;
    }

    public void setAuthorized_at(String authorized_at) {
        this.authorized_at = authorized_at;
    }

    public Integer getReason_code() {
        return reason_code;
    }

    public void setReason_code(Integer reason_code) {
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getAuthorization_code() {
        return authorization_code;
    }

    public void setAuthorization_code(String authorization_code) {
        this.authorization_code = authorization_code;
    }

    public String getAcquirer_transaction_id() {
        return acquirer_transaction_id;
    }

    public void setAcquirer_transaction_id(String acquirer_transaction_id) {
        this.acquirer_transaction_id = acquirer_transaction_id;
    }

    public String getEci() {
        return eci;
    }

    public void setEci(String eci) {
        this.eci = eci;
    }

    public String getUcaf() {
        return ucaf;
    }

    public void setUcaf(String ucaf) {
        this.ucaf = ucaf;
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public Integer getTotal_installment_amount() {
        return total_installment_amount;
    }

    public void setTotal_installment_amount(Integer total_installment_amount) {
        this.total_installment_amount = total_installment_amount;
    }

    public Integer getFirst_installment_amount() {
        return first_installment_amount;
    }

    public void setFirst_installment_amount(Integer first_installment_amount) {
        this.first_installment_amount = first_installment_amount;
    }

    public Integer getOther_installment_amount() {
        return other_installment_amount;
    }

    public void setOther_installment_amount(Integer other_installment_amount) {
        this.other_installment_amount = other_installment_amount;
    }

}
