/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.financiamento.rqrs;

import br.com.infotera.santander.financiamento.model.Artifact;
import br.com.infotera.santander.financiamento.model.Contract;
import br.com.infotera.santander.financiamento.model.Data;
import br.com.infotera.santander.financiamento.model.DocumentGet;
import br.com.infotera.santander.financiamento.model.Form;
import br.com.infotera.santander.financiamento.model.ProposalParam;
import br.com.infotera.santander.financiamento.model.Signature;

/**
 *
 * @author William Dias
 */
public class FormalizacaoDOCRS {
    
    private Integer id;
    private Integer referenceId;
    private Form type;
    private String status;
    private String expirationDate;
    private String updateDateTime;
    private ProposalParam proposal;
    private DocumentGet document;
    private Integer artifactId;
    private Artifact artifact;
    private Data data;
    private Signature signature;
    private Contract contract;
    
    public FormalizacaoDOCRS() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
    }

    public Form getType() {
        return type;
    }

    public void setType(Form type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public ProposalParam getProposal() {
        return proposal;
    }

    public void setProposal(ProposalParam proposal) {
        this.proposal = proposal;
    }

    public DocumentGet getDocument() {
        return document;
    }

    public void setDocument(DocumentGet document) {
        this.document = document;
    }

    public Integer getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(Integer artifactId) {
        this.artifactId = artifactId;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Signature getSignature() {
        return signature;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
    
}
