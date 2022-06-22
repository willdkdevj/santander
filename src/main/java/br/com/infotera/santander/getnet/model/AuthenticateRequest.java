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
public class AuthenticateRequest {

    private String scope, grant_type;

    public AuthenticateRequest(String scope, String grant_type) {
        this.scope = scope;
        this.grant_type = grant_type;
    }

    public AuthenticateRequest() {
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
