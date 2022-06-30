package br.com.infotera.santander.financiamento.rqrs;

import br.com.infotera.santander.financiamento.model.Form;

public class IntegrationCodeRS {

    private Integer id;
    private String code;
    private String desc;
    private Form subsegment;
    private Boolean isEnabledForStore;
    private Boolean isEnabledForSalesman;
    private String isEnabledForClientType;

    public IntegrationCodeRS() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Form getSubsegment() {
        return subsegment;
    }

    public void setSubsegment(Form subsegment) {
        this.subsegment = subsegment;
    }

    public Boolean getIsEnabledForStore() {
        return isEnabledForStore;
    }

    public void setIsEnabledForStore(Boolean isEnabledForStore) {
        this.isEnabledForStore = isEnabledForStore;
    }

    public Boolean getIsEnabledForSalesman() {
        return isEnabledForSalesman;
    }

    public void setIsEnabledForSalesman(Boolean isEnabledForSalesman) {
        this.isEnabledForSalesman = isEnabledForSalesman;
    }

    public String getIsEnabledForClientType() {
        return isEnabledForClientType;
    }

    public void setIsEnabledForClientType(String isEnabledForClientType) {
        this.isEnabledForClientType = isEnabledForClientType;
    }
    
}
