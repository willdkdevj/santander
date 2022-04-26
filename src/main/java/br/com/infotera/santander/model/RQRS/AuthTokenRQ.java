package br.com.infotera.santander.model.RQRS;

public class AuthTokenRQ {

    private String username;
    private String password;
    private String storeId;
    
    public AuthTokenRQ() {
    }

    public AuthTokenRQ(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
    
}
