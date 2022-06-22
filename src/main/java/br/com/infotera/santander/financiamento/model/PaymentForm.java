package br.com.infotera.santander.financiamento.model;

public class PaymentForm {

    private Integer id;

    private String description;

    private String integrationCode;

    public PaymentForm() {
    }

    public PaymentForm(Integer id, String description, String integrationCode) {
        this.id = id;
        this.description = description;
        this.integrationCode = integrationCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIntegrationCode() {
        return integrationCode;
    }

    public void setIntegrationCode(String integrationCode) {
        this.integrationCode = integrationCode;
    }
}
