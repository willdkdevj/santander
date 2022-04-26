/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.model;

/**
 *
 * @author William Dias
 */
public class SimulationInput {
    
    private String proposalId;
    private PreAnalysis preAnalysis;
    private Payment payment;
    private Pricing pricing;

    public SimulationInput() {
    }

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public PreAnalysis getPreAnalysis() {
        return preAnalysis;
    }

    public void setPreAnalysis(PreAnalysis preAnalysis) {
        this.preAnalysis = preAnalysis;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Pricing getPricing() {
        return pricing;
    }

    public void setPricing(Pricing pricing) {
        this.pricing = pricing;
    }
    
}
