package br.com.infotera.santander.financiamento.rqrs;

public class ConsultaRQ {

    private String channel;

    private String storeId;

    public ConsultaRQ() {
    }

    public ConsultaRQ(String channel, String storeId) {
        this.channel = channel;
        this.storeId = storeId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
