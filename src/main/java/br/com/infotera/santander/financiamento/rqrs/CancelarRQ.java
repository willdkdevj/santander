package br.com.infotera.santander.financiamento.rqrs;

import br.com.infotera.santander.financiamento.model.Proposal;

import java.util.List;

public class CancelarRQ {

    private String channel;

    private List<Proposal> proposals;

    private String storeId;

    public CancelarRQ() {
    }

    public CancelarRQ(String channel, List<Proposal> proposals, String storeId) {
        this.channel = channel;
        this.proposals = proposals;
        this.storeId = storeId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
