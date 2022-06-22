package br.com.infotera.santander.financiamento.model;

public class Fee {

    private Boolean tcFee;

    private Boolean tabFee;

    private Boolean tcExemption;

    public Fee() {
    }

    public Fee(Boolean tcFee, Boolean tabFee, Boolean tcExemption) {
        this.tcFee = tcFee;
        this.tabFee = tabFee;
        this.tcExemption = tcExemption;
    }

    public Boolean getTcExemption() {
        return tcExemption;
    }

    public void setTcExemption(Boolean tcExemption) {
        this.tcExemption = tcExemption;
    }

    public Boolean getTcFee() {
        return tcFee;
    }

    public void setTcFee(Boolean tcFee) {
        this.tcFee = tcFee;
    }

    public Boolean getTabFee() {
        return tabFee;
    }

    public void setTabFee(Boolean tabFee) {
        this.tabFee = tabFee;
    }
}
