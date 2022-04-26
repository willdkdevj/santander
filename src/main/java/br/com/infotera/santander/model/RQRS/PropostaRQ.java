package br.com.infotera.santander.model.RQRS;

import br.com.infotera.santander.model.AdditionalInformation;
import br.com.infotera.santander.model.Address;
import br.com.infotera.santander.model.CertifiedAgent;
import br.com.infotera.santander.model.Person;

public class PropostaRQ {

    private CertifiedAgent certifiedAgent;
    private Person person;
    private Address address;
    private AdditionalInformation additionalInformation;
    private Boolean flagIgnoreStageConfirmation;

    public PropostaRQ() {
    }

    public CertifiedAgent getCertifiedAgent() {
        return certifiedAgent;
    }

    public void setCertifiedAgent(CertifiedAgent certifiedAgent) {
        this.certifiedAgent = certifiedAgent;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public AdditionalInformation getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(AdditionalInformation additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public Boolean getFlagIgnoreStageConfirmation() {
        return flagIgnoreStageConfirmation;
    }

    public void setFlagIgnoreStageConfirmation(Boolean flagIgnoreStageConfirmation) {
        this.flagIgnoreStageConfirmation = flagIgnoreStageConfirmation;
    }
    
}
