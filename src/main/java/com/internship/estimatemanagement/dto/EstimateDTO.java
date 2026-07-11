package com.internship.estimatemanagement.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Estimate responses
 */
public class EstimateDTO {

    private Long estimatedId;
    private Long chainId;
    private String groupName;
    private String brandName;
    private String zoneName;
    private String service;
    private Integer qty;
    private Double costPerUnit;
    private Double totalCost;
    private LocalDate deliveryDate;
    private String deliveryDetails;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public EstimateDTO() {}

    public EstimateDTO(Long estimatedId, Long chainId, String groupName, String brandName,
                      String zoneName, String service, Integer qty, Double costPerUnit,
                      Double totalCost, LocalDate deliveryDate, String deliveryDetails,
                      LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.estimatedId = estimatedId;
        this.chainId = chainId;
        this.groupName = groupName;
        this.brandName = brandName;
        this.zoneName = zoneName;
        this.service = service;
        this.qty = qty;
        this.costPerUnit = costPerUnit;
        this.totalCost = totalCost;
        this.deliveryDate = deliveryDate;
        this.deliveryDetails = deliveryDetails;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getEstimatedId() { return estimatedId; }
    public void setEstimatedId(Long estimatedId) { this.estimatedId = estimatedId; }

    public Long getChainId() { return chainId; }
    public void setChainId(Long chainId) { this.chainId = chainId; }

    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }

    public String getZoneName() { return zoneName; }
    public void setZoneName(String zoneName) { this.zoneName = zoneName; }

    public String getService() { return service; }
    public void setService(String service) { this.service = service; }

    public Integer getQty() { return qty; }
    public void setQty(Integer qty) { this.qty = qty; }

    public Double getCostPerUnit() { return costPerUnit; }
    public void setCostPerUnit(Double costPerUnit) { this.costPerUnit = costPerUnit; }

    public Double getTotalCost() { return totalCost; }
    public void setTotalCost(Double totalCost) { this.totalCost = totalCost; }

    public LocalDate getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(LocalDate deliveryDate) { this.deliveryDate = deliveryDate; }

    public String getDeliveryDetails() { return deliveryDetails; }
    public void setDeliveryDetails(String deliveryDetails) { this.deliveryDetails = deliveryDetails; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final EstimateDTO dto = new EstimateDTO();

        public Builder estimatedId(Long estimatedId) { dto.estimatedId = estimatedId; return this; }
        public Builder chainId(Long chainId) { dto.chainId = chainId; return this; }
        public Builder groupName(String groupName) { dto.groupName = groupName; return this; }
        public Builder brandName(String brandName) { dto.brandName = brandName; return this; }
        public Builder zoneName(String zoneName) { dto.zoneName = zoneName; return this; }
        public Builder service(String service) { dto.service = service; return this; }
        public Builder qty(Integer qty) { dto.qty = qty; return this; }
        public Builder costPerUnit(Double costPerUnit) { dto.costPerUnit = costPerUnit; return this; }
        public Builder totalCost(Double totalCost) { dto.totalCost = totalCost; return this; }
        public Builder deliveryDate(LocalDate deliveryDate) { dto.deliveryDate = deliveryDate; return this; }
        public Builder deliveryDetails(String deliveryDetails) { dto.deliveryDetails = deliveryDetails; return this; }
        public Builder createdAt(LocalDateTime createdAt) { dto.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { dto.updatedAt = updatedAt; return this; }
        public EstimateDTO build() { return dto; }
    }
}
