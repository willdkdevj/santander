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
public class Authentication {

    private String token;
    private Integer installment_total_count;
    private String challenge_code;

    public Authentication() {
    }

    public Authentication(String token, Integer installment_total_count, String challenge_code) {
        this.token = token;
        this.installment_total_count = installment_total_count;
        this.challenge_code = challenge_code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getInstallment_total_count() {
        return installment_total_count;
    }

    public void setInstallment_total_count(Integer installment_total_count) {
        this.installment_total_count = installment_total_count;
    }

    public String getChallenge_code() {
        return challenge_code;
    }

    public void setChallenge_code(String challenge_code) {
        this.challenge_code = challenge_code;
    }

}
