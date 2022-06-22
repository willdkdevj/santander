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
public class ThreeDsTokenRequest {

    private String client_code;
    private String currency;
    private String js_version;
    private String order_number;
    private String override_payment_method;
    private Integer total_amount;

    public ThreeDsTokenRequest() {
    }

    public ThreeDsTokenRequest(String client_code, String currency, String js_version, String order_number, String override_payment_method, Integer total_amount) {
        this.client_code = client_code;
        this.currency = currency;
        this.js_version = js_version;
        this.order_number = order_number;
        this.override_payment_method = override_payment_method;
        this.total_amount = total_amount;
    }

    public String getClient_code() {
        return client_code;
    }

    public void setClient_code(String client_code) {
        this.client_code = client_code;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getJs_version() {
        return js_version;
    }

    public void setJs_version(String js_version) {
        this.js_version = js_version;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getOverride_payment_method() {
        return override_payment_method;
    }

    public void setOverride_payment_method(String override_payment_method) {
        this.override_payment_method = override_payment_method;
    }

    public Integer getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Integer total_amount) {
        this.total_amount = total_amount;
    }

}
