package br.com.infotera.santander.financiamento.model;

import java.util.List;

public class Error {

    private List<Message> messages; // "message": "this is a generic technical exception. Please contact {0} and {1}."
    private String statusCode; // " 400
    private String timestamp; // "timestamp": "14-05-2021 01:17:00",
    private String path; // "/register/person",
    private String errorId; // "badrequest.saveproposal.person",
    private String severity; // "ERROR",
    private String type; // "BUSINESS"
    private Fault fault;
    
    public Error() {
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Fault getFault() {
        return fault;
    }

    public void setFault(Fault fault) {
        this.fault = fault;
    }

}
