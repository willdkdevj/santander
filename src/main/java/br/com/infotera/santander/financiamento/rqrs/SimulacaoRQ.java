package br.com.infotera.santander.financiamento.rqrs;

import br.com.infotera.santander.financiamento.model.Lgpd;
import br.com.infotera.santander.financiamento.model.ListTerms;
import br.com.infotera.santander.financiamento.model.PartnerClient;
import br.com.infotera.santander.financiamento.model.Simulation;
import br.com.infotera.santander.financiamento.model.SimulationInput;
import com.google.gson.annotations.SerializedName;

public class SimulacaoRQ {

    private Simulation simulation;
    private PartnerClient partnerClient;
    private ListTerms listTerms;
    private Lgpd lgpd;

    @SerializedName("SimulationInput")
    private SimulationInput simulationInput;
    
    public SimulacaoRQ() {
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public PartnerClient getPartnerClient() {
        return partnerClient;
    }

    public void setPartnerClient(PartnerClient partnerClient) {
        this.partnerClient = partnerClient;
    }

    public ListTerms getListTerms() {
        return listTerms;
    }

    public void setListTerms(ListTerms listTerms) {
        this.listTerms = listTerms;
    }

    public Lgpd getLgpd() {
        return lgpd;
    }

    public void setLgpd(Lgpd lgpd) {
        this.lgpd = lgpd;
    }

    public SimulationInput getSimulationInput() {
        return simulationInput;
    }

    public void setSimulationInput(SimulationInput simulationInput) {
        this.simulationInput = simulationInput;
    }
    
}
