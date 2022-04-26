/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infotera.santander.model;

/**
 *
 * @author William Dias
 */
public class ShopkeeperData {
    
    private Integer minEntryValue; // 0,
    private Boolean isEntryValueAjusted; // false,
    private Double maxFinancedValue; // 100000,
    private Double minFinancedValue; // 500,
    private Boolean isPossibleToIncreaseFinancedValue; // true,
    private Double releasedAmount; // 10000,
    private Boolean showReleasedAmount; // false,
    private Double withheldAmount; // 0,
    private Double retentionPercentageByReleasedAmount; // 0,
    private Double releasedAmountPlusEntry; // 10000,
    private Double retentionPercentageByPurchaseValue; // 0,
    private Boolean riskConditionType; 

    public ShopkeeperData() {
    }

    public Integer getMinEntryValue() {
        return minEntryValue;
    }

    public void setMinEntryValue(Integer minEntryValue) {
        this.minEntryValue = minEntryValue;
    }

    public Boolean getIsEntryValueAjusted() {
        return isEntryValueAjusted;
    }

    public void setIsEntryValueAjusted(Boolean isEntryValueAjusted) {
        this.isEntryValueAjusted = isEntryValueAjusted;
    }

    public Double getMaxFinancedValue() {
        return maxFinancedValue;
    }

    public void setMaxFinancedValue(Double maxFinancedValue) {
        this.maxFinancedValue = maxFinancedValue;
    }

    public Double getMinFinancedValue() {
        return minFinancedValue;
    }

    public void setMinFinancedValue(Double minFinancedValue) {
        this.minFinancedValue = minFinancedValue;
    }

    public Boolean getIsPossibleToIncreaseFinancedValue() {
        return isPossibleToIncreaseFinancedValue;
    }

    public void setIsPossibleToIncreaseFinancedValue(Boolean isPossibleToIncreaseFinancedValue) {
        this.isPossibleToIncreaseFinancedValue = isPossibleToIncreaseFinancedValue;
    }

    public Double getReleasedAmount() {
        return releasedAmount;
    }

    public void setReleasedAmount(Double releasedAmount) {
        this.releasedAmount = releasedAmount;
    }

    public Boolean getShowReleasedAmount() {
        return showReleasedAmount;
    }

    public void setShowReleasedAmount(Boolean showReleasedAmount) {
        this.showReleasedAmount = showReleasedAmount;
    }

    public Double getWithheldAmount() {
        return withheldAmount;
    }

    public void setWithheldAmount(Double withheldAmount) {
        this.withheldAmount = withheldAmount;
    }

    public Double getRetentionPercentageByReleasedAmount() {
        return retentionPercentageByReleasedAmount;
    }

    public void setRetentionPercentageByReleasedAmount(Double retentionPercentageByReleasedAmount) {
        this.retentionPercentageByReleasedAmount = retentionPercentageByReleasedAmount;
    }

    public Double getReleasedAmountPlusEntry() {
        return releasedAmountPlusEntry;
    }

    public void setReleasedAmountPlusEntry(Double releasedAmountPlusEntry) {
        this.releasedAmountPlusEntry = releasedAmountPlusEntry;
    }

    public Double getRetentionPercentageByPurchaseValue() {
        return retentionPercentageByPurchaseValue;
    }

    public void setRetentionPercentageByPurchaseValue(Double retentionPercentageByPurchaseValue) {
        this.retentionPercentageByPurchaseValue = retentionPercentageByPurchaseValue;
    }

    public Boolean getRiskConditionType() {
        return riskConditionType;
    }

    public void setRiskConditionType(Boolean riskConditionType) {
        this.riskConditionType = riskConditionType;
    }
    
}
