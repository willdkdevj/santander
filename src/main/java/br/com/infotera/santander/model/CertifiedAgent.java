/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.model;

/**
 *
 * @author William Dias
 */
public class CertifiedAgent {
    
    private String id;
    private String proposalId;
    private String agentName;
    private String documentNumber;
    private String agentCode;
    private String certificationNumber;
    private String expirationDate;
    private String relationshipDate;
    private String deadlineCertificationDate;
    private String validDaysCertification;
    private String daysEndLink;
    private String agentCertifiedNumber;
    
    public CertifiedAgent() {
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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getCertificationNumber() {
        return certificationNumber;
    }

    public void setCertificationNumber(String certificationNumber) {
        this.certificationNumber = certificationNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getRelationshipDate() {
        return relationshipDate;
    }

    public void setRelationshipDate(String relationshipDate) {
        this.relationshipDate = relationshipDate;
    }

    public String getDeadlineCertificationDate() {
        return deadlineCertificationDate;
    }

    public void setDeadlineCertificationDate(String deadlineCertificationDate) {
        this.deadlineCertificationDate = deadlineCertificationDate;
    }

    public String getValidDaysCertification() {
        return validDaysCertification;
    }

    public void setValidDaysCertification(String validDaysCertification) {
        this.validDaysCertification = validDaysCertification;
    }

    public String getDaysEndLink() {
        return daysEndLink;
    }

    public void setDaysEndLink(String daysEndLink) {
        this.daysEndLink = daysEndLink;
    }

    public String getAgentCertifiedNumber() {
        return agentCertifiedNumber;
    }

    public void setAgentCertifiedNumber(String agentCertifiedNumber) {
        this.agentCertifiedNumber = agentCertifiedNumber;
    }
    
}
