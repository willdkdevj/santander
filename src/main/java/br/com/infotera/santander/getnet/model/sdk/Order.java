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
public class Order {

    private String order_id;
    private int sales_tax;
    private String product_type;

    public Order() {
    }

    public Order(String order_id, int sales_tax, String product_type) {
        this.order_id = order_id;
        this.sales_tax = sales_tax;
        this.product_type = product_type;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getSales_tax() {
        return sales_tax;
    }

    public void setSales_tax(int sales_tax) {
        this.sales_tax = sales_tax;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

}
