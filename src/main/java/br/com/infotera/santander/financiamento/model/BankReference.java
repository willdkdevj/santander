package br.com.infotera.santander.financiamento.model;

public class BankReference {

    private Integer accountCode;

    private String accountDigit;

    private Integer agencyCode;

    private Integer bankCode;

    public BankReference() {
    }

    public BankReference(Integer accountCode, String accountDigit, Integer agencyCode, Integer bankCode) {
        this.accountCode = accountCode;
        this.accountDigit = accountDigit;
        this.agencyCode = agencyCode;
        this.bankCode = bankCode;
    }

    public Integer getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(Integer accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountDigit() {
        return accountDigit;
    }

    public void setAccountDigit(String accountDigit) {
        this.accountDigit = accountDigit;
    }

    public Integer getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(Integer agencyCode) {
        this.agencyCode = agencyCode;
    }

    public Integer getBankCode() {
        return bankCode;
    }

    public void setBankCode(Integer bankCode) {
        this.bankCode = bankCode;
    }
}
