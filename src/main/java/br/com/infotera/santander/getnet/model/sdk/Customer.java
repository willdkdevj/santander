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
public class Customer {

    private String customer_id, first_name, last_name, name, email, document_type, document_number, phone_number;
    private BillingAddress billing_address;

    public Customer() {
    }

    public Customer(String customer_id, String first_name, String last_name, String name, String email, String document_type, String document_number, String phone_number, BillingAddress billing_address) {
        this.customer_id = customer_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.name = name;
        this.email = email;
        this.document_type = document_type;
        this.document_number = document_number;
        this.phone_number = phone_number;
        this.billing_address = billing_address;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocument_type() {
        return document_type;
    }

    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }

    public String getDocument_number() {
        return document_number;
    }

    public void setDocument_number(String document_number) {
        this.document_number = document_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public BillingAddress getBilling_address() {
        return billing_address;
    }

    public void setBilling_address(BillingAddress billing_address) {
        this.billing_address = billing_address;
    }

}
