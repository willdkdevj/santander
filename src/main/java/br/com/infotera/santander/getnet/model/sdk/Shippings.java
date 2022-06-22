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
public class Shippings {

    private String first_name, name, email, phone_number;
    private int shipping_amount;
    private Address address;

    public Shippings() {
    }

    public Shippings(String phone_number) {
        this.phone_number = phone_number;
    }

    public Shippings(String first_name, String name, String email, String phone_number, int shipping_amount, Address address) {
        this.first_name = first_name;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.shipping_amount = shipping_amount;
        this.address = address;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getShipping_amount() {
        return shipping_amount;
    }

    public void setShipping_amount(int shipping_amount) {
        this.shipping_amount = shipping_amount;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
