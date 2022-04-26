/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.model;

import java.util.List;

/**
 *
 * @author William Dias
 */
public class Data {
    
    private List<Terms> listTerms;
    private List<CheckListValidate> listCheckValidate;
    public Data() {
    }

    public List<Terms> getListTerms() {
        return listTerms;
    }

    public void setListTerms(List<Terms> listTerms) {
        this.listTerms = listTerms;
    }

    public List<CheckListValidate> getListCheckValidate() {
        return listCheckValidate;
    }

    public void setListCheckValidate(List<CheckListValidate> listCheckValidate) {
        this.listCheckValidate = listCheckValidate;
    }
    
}
