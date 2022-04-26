/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.model;

import java.util.List;

/**
 *
 * @author William Dias
 */
public class Person {
    
    private String id;
    private String proposalId;
    private String clientId;
    private Boolean mainFlag;
    private String type;
    private String name;
    private String documentId;
    private String documentNumber;
    private Person person;
    private Email emails;
    private Phone phones;
    private Physical physical;
    private List<Address> addresses;

    public Person() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Boolean getMainFlag() {
        return mainFlag;
    }

    public void setMainFlag(Boolean mainFlag) {
        this.mainFlag = mainFlag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Email getEmails() {
        return emails;
    }

    public void setEmails(Email emails) {
        this.emails = emails;
    }

    public Phone getPhones() {
        return phones;
    }

    public void setPhones(Phone phones) {
        this.phones = phones;
    }

    public Physical getPhysical() {
        return physical;
    }

    public void setPhysical(Physical physical) {
        this.physical = physical;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
    
}
