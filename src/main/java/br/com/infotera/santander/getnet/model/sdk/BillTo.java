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
public class BillTo {

    private String address1;
    private String address2;
    private String administrative_area;
    private String country;
    private String email;
    private String first_name;
    private String last_name;
    private String locality;
    private String mobile_phone;
    private String phone_number;
    private String postal_code;

    public BillTo() {
    }

    public BillTo(String address1, String address2, String administrative_area, String country, String email, String first_name, String last_name, String locality, String mobile_phone, String phone_number, String postal_code) {
        this.address1 = address1;
        this.address2 = address2;
        this.administrative_area = administrative_area;
        this.country = country;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.locality = locality;
        this.mobile_phone = mobile_phone;
        this.phone_number = phone_number;
        this.postal_code = postal_code;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAdministrative_area() {
        return administrative_area;
    }

    public void setAdministrative_area(String administrative_area) {
        this.administrative_area = administrative_area;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

}
