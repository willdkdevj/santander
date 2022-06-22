package br.com.infotera.santander.financiamento.rqrs;

public class BoletoRQ {

    private Integer idPropostaLegado;

    public BoletoRQ() {
    }

    public BoletoRQ(Integer idPropostaLegado) {
        this.idPropostaLegado = idPropostaLegado;
    }

    public Integer getIdPropostaLegado() {
        return idPropostaLegado;
    }

    public void setIdPropostaLegado(Integer idPropostaLegado) {
        this.idPropostaLegado = idPropostaLegado;
    }
}
