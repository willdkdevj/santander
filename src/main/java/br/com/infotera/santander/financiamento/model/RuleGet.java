/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.financiamento.model;

/**
 *
 * @author william
 */
public class RuleGet extends DocumentGet {
    
    private Long id;

    public RuleGet() {
        super();
    }

    public RuleGet(Long id) {
        this.id = id;
    }

    public Long getIdChild() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
   
}
