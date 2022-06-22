/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infotera.santander.getnet.model.sdk;

/**
 *
 * @author enioj
 */
public class ThreeDSOrder {

    private String product_code;
    private String currency;
    private Integer total_amount;

    private BillTo bill_to;

    public ThreeDSOrder() {
    }

    public ThreeDSOrder(String product_code, String currency, Integer total_amount, BillTo bill_to) {
        this.product_code = product_code;
        this.currency = currency;
        this.total_amount = total_amount;
        this.bill_to = bill_to;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Integer total_amount) {
        this.total_amount = total_amount;
    }

    public BillTo getBill_to() {
        return bill_to;
    }

    public void setBill_to(BillTo bill_to) {
        this.bill_to = bill_to;
    }

}
