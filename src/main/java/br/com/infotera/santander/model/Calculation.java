/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.model;

/**
 *
 * @author William Dias
 */
public class Calculation {
    
    private Integer id; // 3651,
    private Integer proposalId; // 60654,
    private Double minEntryValue; // 0,
    private Double recommendedEntryValue; // 0,
    private Double minPmtValue; // 1,
    private Double maxPmtValue; // 100000,
    private Boolean termErrorFlag; // false,
    private Integer minTermAmount; // 3,
    private Integer maxTermAmount; // 24,
    private Double minApprovedValue; // 500,
    private Double maxApprovedValue; // 100000,
    private Boolean bannerShowFlag; // false,
    private Integer maxBannerValue;

    public Calculation() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProposalId() {
        return proposalId;
    }

    public void setProposalId(Integer proposalId) {
        this.proposalId = proposalId;
    }

    public Double getMinEntryValue() {
        return minEntryValue;
    }

    public void setMinEntryValue(Double minEntryValue) {
        this.minEntryValue = minEntryValue;
    }

    public Double getRecommendedEntryValue() {
        return recommendedEntryValue;
    }

    public void setRecommendedEntryValue(Double recommendedEntryValue) {
        this.recommendedEntryValue = recommendedEntryValue;
    }

    public Double getMinPmtValue() {
        return minPmtValue;
    }

    public void setMinPmtValue(Double minPmtValue) {
        this.minPmtValue = minPmtValue;
    }

    public Double getMaxPmtValue() {
        return maxPmtValue;
    }

    public void setMaxPmtValue(Double maxPmtValue) {
        this.maxPmtValue = maxPmtValue;
    }

    public Boolean getTermErrorFlag() {
        return termErrorFlag;
    }

    public void setTermErrorFlag(Boolean termErrorFlag) {
        this.termErrorFlag = termErrorFlag;
    }

    public Integer getMinTermAmount() {
        return minTermAmount;
    }

    public void setMinTermAmount(Integer minTermAmount) {
        this.minTermAmount = minTermAmount;
    }

    public Integer getMaxTermAmount() {
        return maxTermAmount;
    }

    public void setMaxTermAmount(Integer maxTermAmount) {
        this.maxTermAmount = maxTermAmount;
    }

    public Double getMinApprovedValue() {
        return minApprovedValue;
    }

    public void setMinApprovedValue(Double minApprovedValue) {
        this.minApprovedValue = minApprovedValue;
    }

    public Double getMaxApprovedValue() {
        return maxApprovedValue;
    }

    public void setMaxApprovedValue(Double maxApprovedValue) {
        this.maxApprovedValue = maxApprovedValue;
    }

    public Boolean getBannerShowFlag() {
        return bannerShowFlag;
    }

    public void setBannerShowFlag(Boolean bannerShowFlag) {
        this.bannerShowFlag = bannerShowFlag;
    }

    public Integer getMaxBannerValue() {
        return maxBannerValue;
    }

    public void setMaxBannerValue(Integer maxBannerValue) {
        this.maxBannerValue = maxBannerValue;
    }
    
}
