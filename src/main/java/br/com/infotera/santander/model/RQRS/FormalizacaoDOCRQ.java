/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.model.RQRS;

import br.com.infotera.santander.model.Client;
import br.com.infotera.santander.model.Proposal;

/**
 *
 * @author William Dias
 */
public class FormalizacaoDOCRQ {
    
    private String proposalId;
    private String file;
    private String checklistId;
    private Client client;
    private Proposal proposal;
    private Terms billet;
    
    public FormalizacaoDOCRQ() {
    }

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(String checklistId) {
        this.checklistId = checklistId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }

    public Terms getBillet() {
        return billet;
    }

    public void setBillet(Terms billet) {
        this.billet = billet;
    }
    
}
