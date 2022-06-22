package br.com.infotera.santander.financiamento.rqrs;

public class CancelarRS {

    private Integer response;

    public CancelarRS() {
    }

    public CancelarRS(Integer response) {
        this.response = response;
    }

    public Integer getResponse() {
        return response;
    }

    public void setResponse(Integer response) {
        this.response = response;
    }
}
