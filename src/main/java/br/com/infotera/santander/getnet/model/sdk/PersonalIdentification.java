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
public class PersonalIdentification {

    private String customer_id;
    private String customer_type_id;

    public PersonalIdentification() {
    }

    public PersonalIdentification(String customer_id, String customer_type_id) {
        this.customer_id = customer_id;
        this.customer_type_id = customer_type_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_type_id() {
        return customer_type_id;
    }

    public void setCustomer_type_id(String customer_type_id) {
        this.customer_type_id = customer_type_id;
    }

}
