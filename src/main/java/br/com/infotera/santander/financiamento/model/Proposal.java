package br.com.infotera.santander.financiamento.model;

public class Proposal {

    private String id; // "60654",
    private String creationDate; // "2021-08-20T00:00:00",
    private String expirationDate; // "2021-09-19",
    private String product; // "CDC",
    private String client; // "F",
    private Boolean isTravelSubSegment; // false,
    private Boolean recoveredDataByCIPorPE; // false,
    private String storeCode; // "446764
    private String typeBipartite;
    private Boolean flgBipartite;
    
    public Proposal() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Boolean getIsTravelSubSegment() {
        return isTravelSubSegment;
    }

    public void setIsTravelSubSegment(Boolean isTravelSubSegment) {
        this.isTravelSubSegment = isTravelSubSegment;
    }

    public Boolean getRecoveredDataByCIPorPE() {
        return recoveredDataByCIPorPE;
    }

    public void setRecoveredDataByCIPorPE(Boolean recoveredDataByCIPorPE) {
        this.recoveredDataByCIPorPE = recoveredDataByCIPorPE;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getTypeBipartite() {
        return typeBipartite;
    }

    public void setTypeBipartite(String typeBipartite) {
        this.typeBipartite = typeBipartite;
    }

    public Boolean getFlgBipartite() {
        return flgBipartite;
    }

    public void setFlgBipartite(Boolean flgBipartite) {
        this.flgBipartite = flgBipartite;
    }
    
}
