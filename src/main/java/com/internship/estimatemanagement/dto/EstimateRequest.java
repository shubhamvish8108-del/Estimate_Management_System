package com.internship.estimatemanagement.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;


public class EstimateRequest {

    @NotNull(message = "Chain ID is required")
    private Long chainId;

    private String groupName;
    private String brandName;
    private String zoneName;
    private String service;

    @Positive(message = "Quantity must be positive")
    private Integer qty;

    @Positive(message = "Cost per unit must be positive")
    private Double costPerUnit;

    private LocalDate deliveryDate;
    private String deliveryDetails;

    
    public EstimateRequest() {}

    public EstimateRequest(Long chainId, String groupName, String brandName, String zoneName,
                          String service, Integer qty, Double costPerUnit, LocalDate deliveryDate,
                          String deliveryDetails) {
        this.chainId = chainId;
        this.groupName = groupName;
        this.brandName = brandName;
        this.zoneName = zoneName;
        this.service = service;
        this.qty = qty;
        this.costPerUnit = costPerUnit;
        this.deliveryDate = deliveryDate;
        this.deliveryDetails = deliveryDetails;
    }

  
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

    public LocalDate getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(LocalDate deliveryDate) { this.deliveryDate = deliveryDate; }

    public String getDeliveryDetails() { return deliveryDetails; }
    public void setDeliveryDetails(String deliveryDetails) { this.deliveryDetails = deliveryDetails; }
}
