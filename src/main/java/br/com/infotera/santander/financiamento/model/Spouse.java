package br.com.infotera.santander.financiamento.model;

public class Spouse {

    private String document;

    private String name;

    public Spouse() {
    }

    public Spouse(String document, String name) {
        this.document = document;
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
