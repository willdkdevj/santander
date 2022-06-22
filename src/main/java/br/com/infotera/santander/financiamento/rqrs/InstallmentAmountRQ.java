package br.com.infotera.santander.financiamento.rqrs;

public class InstallmentAmountRQ {

    private Integer storeId;

    public InstallmentAmountRQ() {
    }

    public InstallmentAmountRQ(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
}
