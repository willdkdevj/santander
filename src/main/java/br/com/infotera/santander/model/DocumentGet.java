package br.com.infotera.santander.model;

import java.util.List;

public class DocumentGet {

    private Integer id;
    private String code;
    private String name;
    private String description;
    private String allowedTypes;
    private List<String> integrationTypes;

    public DocumentGet() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAllowedTypes() {
        return allowedTypes;
    }

    public void setAllowedTypes(String allowedTypes) {
        this.allowedTypes = allowedTypes;
    }

    public List<String> getIntegrationTypes() {
        return integrationTypes;
    }

    public void setIntegrationTypes(List<String> integrationTypes) {
        this.integrationTypes = integrationTypes;
    }

}
