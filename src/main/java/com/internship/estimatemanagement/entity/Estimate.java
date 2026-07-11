package com.internship.estimatemanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "estimates")
public class Estimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estimated_id")
    private Long estimatedId;

    @Column(name = "chain_id", nullable = false)
    private Long chainId;

    @Column(name = "group_name", length = 50)
    private String groupName;

    @Column(name = "brand_name", length = 50)
    private String brandName;

    @Column(name = "zone_name", length = 50)
    private String zoneName;

    @Column(name = "service", length = 100)
    private String service;

    @Column(name = "qty")
    private Integer qty;

    @Column(name = "cost_per_unit")
    private Double costPerUnit;

    @Column(name = "total_cost")
    private Double totalCost;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "delivery_details", length = 100)
    private String deliveryDetails;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

   
    public Estimate() {}

    public Estimate(Long estimatedId, Long chainId, String groupName, String brandName,
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

   
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (qty != null && costPerUnit != null && totalCost == null) {
            totalCost = qty * costPerUnit;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        if (qty != null && costPerUnit != null) {
            totalCost = qty * costPerUnit;
        }
    }

   
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final Estimate estimate = new Estimate();

        public Builder estimatedId(Long estimatedId) { estimate.estimatedId = estimatedId; return this; }
        public Builder chainId(Long chainId) { estimate.chainId = chainId; return this; }
        public Builder groupName(String groupName) { estimate.groupName = groupName; return this; }
        public Builder brandName(String brandName) { estimate.brandName = brandName; return this; }
        public Builder zoneName(String zoneName) { estimate.zoneName = zoneName; return this; }
        public Builder service(String service) { estimate.service = service; return this; }
        public Builder qty(Integer qty) { estimate.qty = qty; return this; }
        public Builder costPerUnit(Double costPerUnit) { estimate.costPerUnit = costPerUnit; return this; }
        public Builder totalCost(Double totalCost) { estimate.totalCost = totalCost; return this; }
        public Builder deliveryDate(LocalDate deliveryDate) { estimate.deliveryDate = deliveryDate; return this; }
        public Builder deliveryDetails(String deliveryDetails) { estimate.deliveryDetails = deliveryDetails; return this; }
        public Builder createdAt(LocalDateTime createdAt) { estimate.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { estimate.updatedAt = updatedAt; return this; }
        public Estimate build() { return estimate; }
    }
}
