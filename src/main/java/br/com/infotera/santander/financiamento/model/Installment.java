/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.financiamento.model;

/**
 *
 * @author William Dias
 */
public class Installment {
    
    private Integer number;
    private String dueDate;
    private String dueDateFormated;
    private Double value;

    public Installment() {
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueDateFormated() {
        return dueDateFormated;
    }

    public void setDueDateFormated(String dueDateFormated) {
        this.dueDateFormated = dueDateFormated;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
    
}
