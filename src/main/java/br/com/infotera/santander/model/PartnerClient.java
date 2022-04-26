/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.model;

/**
 *
 * @author William Dias
 */
public class PartnerClient {
    
    private String documentType; // "CPF",
    private String documentNumber; // "66830748950",
    private String birthdayDate; // "1985-09-11",
    private String mainTelephoneAreaCode; // "11",
    private String mainTelephoneNumber;

    public PartnerClient() {
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(String birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public String getMainTelephoneAreaCode() {
        return mainTelephoneAreaCode;
    }

    public void setMainTelephoneAreaCode(String mainTelephoneAreaCode) {
        this.mainTelephoneAreaCode = mainTelephoneAreaCode;
    }

    public String getMainTelephoneNumber() {
        return mainTelephoneNumber;
    }

    public void setMainTelephoneNumber(String mainTelephoneNumber) {
        this.mainTelephoneNumber = mainTelephoneNumber;
    }
    
    
}
