/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.financiamento.model;

import java.util.List;

/**
 *
 * @author william
 */
public class ProposalParam {
    
    private Integer id;
    private List<Terms> documentTypes;
    private List<Terms> genders;
    private List<Terms> nationalities;
    private List<Terms> states;
    private List<Terms> banks;
    private List<Terms> accountTypes;
    private List<Terms> addressTypes;
    private List<Terms> professions;
    private List<Terms> companyTypes;
    private List<Terms> legalNatures;
    private List<Terms> economicActivityGroups;
    private List<Terms> economicActivities;
    private List<Terms> legalRepresentativeRelationships;

    public ProposalParam() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Terms> getDocumentTypes() {
        return documentTypes;
    }

    public void setDocumentTypes(List<Terms> documentTypes) {
        this.documentTypes = documentTypes;
    }

    public List<Terms> getGenders() {
        return genders;
    }

    public void setGenders(List<Terms> genders) {
        this.genders = genders;
    }

    public List<Terms> getNationalities() {
        return nationalities;
    }

    public void setNationalities(List<Terms> nationalities) {
        this.nationalities = nationalities;
    }

    public List<Terms> getStates() {
        return states;
    }

    public void setStates(List<Terms> states) {
        this.states = states;
    }

    public List<Terms> getBanks() {
        return banks;
    }

    public void setBanks(List<Terms> banks) {
        this.banks = banks;
    }

    public List<Terms> getAccountTypes() {
        return accountTypes;
    }

    public void setAccountTypes(List<Terms> accountTypes) {
        this.accountTypes = accountTypes;
    }

    public List<Terms> getAddressTypes() {
        return addressTypes;
    }

    public void setAddressTypes(List<Terms> addressTypes) {
        this.addressTypes = addressTypes;
    }

    public List<Terms> getProfessions() {
        return professions;
    }

    public void setProfessions(List<Terms> professions) {
        this.professions = professions;
    }

    public List<Terms> getCompanyTypes() {
        return companyTypes;
    }

    public void setCompanyTypes(List<Terms> companyTypes) {
        this.companyTypes = companyTypes;
    }

    public List<Terms> getLegalNatures() {
        return legalNatures;
    }

    public void setLegalNatures(List<Terms> legalNatures) {
        this.legalNatures = legalNatures;
    }

    public List<Terms> getEconomicActivityGroups() {
        return economicActivityGroups;
    }

    public void setEconomicActivityGroups(List<Terms> economicActivityGroups) {
        this.economicActivityGroups = economicActivityGroups;
    }

    public List<Terms> getEconomicActivities() {
        return economicActivities;
    }

    public void setEconomicActivities(List<Terms> economicActivities) {
        this.economicActivities = economicActivities;
    }

    public List<Terms> getLegalRepresentativeRelationships() {
        return legalRepresentativeRelationships;
    }

    public void setLegalRepresentativeRelationships(List<Terms> legalRepresentativeRelationships) {
        this.legalRepresentativeRelationships = legalRepresentativeRelationships;
    }
    
}
