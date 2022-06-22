/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infotera.santander.getnet.model.sdk;

import java.util.List;

/**
 *
 * @author danilo
 */
public class ErrorBase {

    private String message, name, error, error_description;
    private int status_code;
    private List<Details> details;

    public ErrorBase() {
    }

    public ErrorBase(String message, String name, int status_code, List<Details> details) {
        this.message = message;
        this.name = name;
        this.status_code = status_code;
        this.details = details;
    }

    public ErrorBase(String error, String error_description) {
        this.error = error;
        this.error_description = error_description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public List<Details> getDetails() {
        return details;
    }

    public void setDetails(List<Details> details) {
        this.details = details;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

}
