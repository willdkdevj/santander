/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.model;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author William Dias
 */
public class Contract {
    
    private String data;
    private String name;
    private String uuid;
    private DocumentGet document;

    public Contract() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DocumentGet getDocument() {
        return document;
    }

    public void setDocument(DocumentGet document) {
        this.document = document;
    }
    
}
