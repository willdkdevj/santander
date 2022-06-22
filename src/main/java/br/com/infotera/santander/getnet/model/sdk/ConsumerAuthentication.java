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
public class ConsumerAuthentication {

    private String acs_url;
    private String authentication_path;
    private String authentication_transaction_id;
    private String pareq;
    private String proof_xml;
    private String proxy_pan;
    private String specification_version;
    private String veres_enrolled;
    private String xid;
    private String token;

    // authentication-result
    private String authentication_result;
    private String authentication_status_msg;
    private String indicator;
    private String eci;
    private String pares_status;
    private String cavv_algorithm;
    private String ucaf;
    private String ucaf_authentication_data;
    private String ucaf_collection_indicator;
    private String cavv;

    public ConsumerAuthentication() {
    }

    public String getAcs_url() {
        return acs_url;
    }

    public void setAcs_url(String acs_url) {
        this.acs_url = acs_url;
    }

    public String getAuthentication_path() {
        return authentication_path;
    }

    public void setAuthentication_path(String authentication_path) {
        this.authentication_path = authentication_path;
    }

    public String getAuthentication_transaction_id() {
        return authentication_transaction_id;
    }

    public void setAuthentication_transaction_id(String authentication_transaction_id) {
        this.authentication_transaction_id = authentication_transaction_id;
    }

    public String getPareq() {
        return pareq;
    }

    public void setPareq(String pareq) {
        this.pareq = pareq;
    }

    public String getProof_xml() {
        return proof_xml;
    }

    public void setProof_xml(String proof_xml) {
        this.proof_xml = proof_xml;
    }

    public String getProxy_pan() {
        return proxy_pan;
    }

    public void setProxy_pan(String proxy_pan) {
        this.proxy_pan = proxy_pan;
    }

    public String getSpecification_version() {
        return specification_version;
    }

    public void setSpecification_version(String specification_version) {
        this.specification_version = specification_version;
    }

    public String getVeres_enrolled() {
        return veres_enrolled;
    }

    public void setVeres_enrolled(String veres_enrolled) {
        this.veres_enrolled = veres_enrolled;
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAuthentication_result() {
        return authentication_result;
    }

    public void setAuthentication_result(String authentication_result) {
        this.authentication_result = authentication_result;
    }

    public String getAuthentication_status_msg() {
        return authentication_status_msg;
    }

    public void setAuthentication_status_msg(String authentication_status_msg) {
        this.authentication_status_msg = authentication_status_msg;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String getEci() {
        return eci;
    }

    public void setEci(String eci) {
        this.eci = eci;
    }

    public String getPares_status() {
        return pares_status;
    }

    public void setPares_status(String pares_status) {
        this.pares_status = pares_status;
    }

    public String getCavv_algorithm() {
        return cavv_algorithm;
    }

    public void setCavv_algorithm(String cavv_algorithm) {
        this.cavv_algorithm = cavv_algorithm;
    }

    public String getUcaf() {
        return ucaf;
    }

    public void setUcaf(String ucaf) {
        this.ucaf = ucaf;
    }

    public String getUcaf_authentication_data() {
        return ucaf_authentication_data;
    }

    public void setUcaf_authentication_data(String ucaf_authentication_data) {
        this.ucaf_authentication_data = ucaf_authentication_data;
    }

    public String getUcaf_collection_indicator() {
        return ucaf_collection_indicator;
    }

    public void setUcaf_collection_indicator(String ucaf_collection_indicator) {
        this.ucaf_collection_indicator = ucaf_collection_indicator;
    }

    public String getCavv() {
        return cavv;
    }

    public void setCavv(String cavv) {
        this.cavv = cavv;
    }

}
