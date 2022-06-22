/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infotera.santander.getnet.model;

import br.com.infotera.santander.getnet.model.sdk.Customer;
import br.com.infotera.santander.getnet.model.sdk.Device;
import br.com.infotera.santander.getnet.model.sdk.Order;
import br.com.infotera.santander.getnet.model.sdk.Shippings;
import java.util.List;

/**
 *
 * @author enioj
 */
public class AuthorizationRequest {

    private String seller_id;
    private int amount;
    private String currency;
    private Order order;
    private Customer customer;
    private Device device;
    private List<Shippings> shippings;
    private CreditRequest credit;

    public AuthorizationRequest() {
    }

    public AuthorizationRequest(String seller_id, int amount, String currency, Order order, Customer customer, CreditRequest credit) {
        this.seller_id = seller_id;
        this.amount = amount;
        this.currency = currency;
        this.order = order;
        this.customer = customer;
        this.credit = credit;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public List<Shippings> getShippings() {
        return shippings;
    }

    public void setShippings(List<Shippings> shippingsList) {
        this.shippings = shippingsList;
    }

    public CreditRequest getCredit() {
        return credit;
    }

    public void setCredit(CreditRequest credit) {
        this.credit = credit;
    }
}
