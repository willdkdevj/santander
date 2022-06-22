package br.com.infotera.santander.financiamento.model;

public class Guarantor {

    private Address address;

    public Guarantor() {
    }

    public Guarantor(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
