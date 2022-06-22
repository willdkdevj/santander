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
public class Details {

    private String status, error_code, description, description_detail;

    public Details() {
    }

    public Details(String status, String error_code, String description, String description_detail) {
        this.status = status;
        this.error_code = error_code;
        this.description = description;
        this.description_detail = description_detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription_details() {
        return description_detail;
    }

    public void setDescription_details(String description_detail) {
        this.description_detail = description_detail;
    }

}
