/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.financiamento.model;

/**
 *
 * @author William Dias
 */
public class ChannelSupplier {
    
    private String id;
    private String code;
    private String name;
    private Bipartite bipartite;

    public ChannelSupplier() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bipartite getBipartite() {
        return bipartite;
    }

    public void setBipartite(Bipartite bipartite) {
        this.bipartite = bipartite;
    }
    
}
