package com.internship.estimatemanagement.dto;


public class DashboardStats {

    private Long totalEstimates;
    private Double totalEstimatedAmount;
    private Long estimatesThisMonth;
    private Long uniqueChains;

    
    public DashboardStats() {}

    public DashboardStats(Long totalEstimates, Double totalEstimatedAmount,
                         Long estimatesThisMonth, Long uniqueChains) {
        this.totalEstimates = totalEstimates;
        this.totalEstimatedAmount = totalEstimatedAmount;
        this.estimatesThisMonth = estimatesThisMonth;
        this.uniqueChains = uniqueChains;
    }

    
    public Long getTotalEstimates() { return totalEstimates; }
    public void setTotalEstimates(Long totalEstimates) { this.totalEstimates = totalEstimates; }

    public Double getTotalEstimatedAmount() { return totalEstimatedAmount; }
    public void setTotalEstimatedAmount(Double totalEstimatedAmount) { this.totalEstimatedAmount = totalEstimatedAmount; }

    public Long getEstimatesThisMonth() { return estimatesThisMonth; }
    public void setEstimatesThisMonth(Long estimatesThisMonth) { this.estimatesThisMonth = estimatesThisMonth; }

    public Long getUniqueChains() { return uniqueChains; }
    public void setUniqueChains(Long uniqueChains) { this.uniqueChains = uniqueChains; }

    
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final DashboardStats stats = new DashboardStats();

        public Builder totalEstimates(Long totalEstimates) { stats.totalEstimates = totalEstimates; return this; }
        public Builder totalEstimatedAmount(Double totalEstimatedAmount) { stats.totalEstimatedAmount = totalEstimatedAmount; return this; }
        public Builder estimatesThisMonth(Long estimatesThisMonth) { stats.estimatesThisMonth = estimatesThisMonth; return this; }
        public Builder uniqueChains(Long uniqueChains) { stats.uniqueChains = uniqueChains; return this; }
        public DashboardStats build() { return stats; }
    }
}
