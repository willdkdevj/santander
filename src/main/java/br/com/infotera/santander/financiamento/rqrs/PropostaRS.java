package br.com.infotera.santander.financiamento.rqrs;

import br.com.infotera.santander.financiamento.model.ClientData;
import br.com.infotera.santander.financiamento.model.ProposalParam;

public class PropostaRS {

    private Integer id;
    private String proposalId;
    private String code;
    private Boolean validated;
    private ClientData clientData;
    private ProposalParam proposalParams;
    
    public PropostaRS() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    public ClientData getClientData() {
        return clientData;
    }

    public void setClientData(ClientData clientData) {
        this.clientData = clientData;
    }

    public ProposalParam getProposalParams() {
        return proposalParams;
    }

    public void setProposalParams(ProposalParam proposalParams) {
        this.proposalParams = proposalParams;
    }

}
