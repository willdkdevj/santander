/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.model;

/**
 *
 * @author William Dias
 */
class CheckListValidate {
    
    private String checklistId;
    private Client client;
    private Terms type;
    private Object artifact;
    private DocumentGet document;

    public CheckListValidate() {
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

    public Terms getType() {
        return type;
    }

    public void setType(Terms type) {
        this.type = type;
    }

    public Object getArtifact() {
        return artifact;
    }

    public void setArtifact(Object artifact) {
        this.artifact = artifact;
    }

    public DocumentGet getDocument() {
        return document;
    }

    public void setDocument(DocumentGet document) {
        this.document = document;
    }
    
}
