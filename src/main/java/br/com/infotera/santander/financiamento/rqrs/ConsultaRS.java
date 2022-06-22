package br.com.infotera.santander.financiamento.rqrs;

public class ConsultaRS {

    private String status;

    private String statusDescription;

    private boolean frozen;

    private String proposalExternalId;

    private Integer approvalNumber;

    public ConsultaRS() {
    }

    public ConsultaRS(String status, String statusDescription, boolean frozen, String proposalExternalId, Integer approvalNumber) {
        this.status = status;
        this.statusDescription = statusDescription;
        this.frozen = frozen;
        this.proposalExternalId = proposalExternalId;
        this.approvalNumber = approvalNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public String getProposalExternalId() {
        return proposalExternalId;
    }

    public void setProposalExternalId(String proposalExternalId) {
        this.proposalExternalId = proposalExternalId;
    }

    public Integer getApprovalNumber() {
        return approvalNumber;
    }

    public void setApprovalNumber(Integer approvalNumber) {
        this.approvalNumber = approvalNumber;
    }
}
