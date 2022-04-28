/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.model;

/**
 *
 * @author William Dias
 */
public class FormEncoded {
    
    private String grant_type;
    private String username;
    private String password;
    private Integer businessCode;
    private Integer loginTypeId;
    private Integer tpLoginCode;
    private Integer storeId;
    private Boolean revokeSession;

    public FormEncoded() {
    }

    public String getGrantType() {
        return grant_type;
    }

    public void setGrantType(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public Integer getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(Integer businessCode) {
        this.businessCode = businessCode;
    }

    public Integer getLoginTypeId() {
        return loginTypeId;
    }

    public void setLoginTypeId(Integer loginTypeId) {
        this.loginTypeId = loginTypeId;
    }

    public Integer getTpLoginCode() {
        return tpLoginCode;
    }

    public void setTpLoginCode(Integer tpLoginCode) {
        this.tpLoginCode = tpLoginCode;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Boolean getRevokeSession() {
        return revokeSession;
    }

    public void setRevokeSession(Boolean revokeSession) {
        this.revokeSession = revokeSession;
    }
    
}
