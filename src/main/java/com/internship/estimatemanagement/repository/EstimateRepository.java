package com.internship.estimatemanagement.repository;

import com.internship.estimatemanagement.entity.Estimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Estimate entity operations.
 */
@Repository
public interface EstimateRepository extends JpaRepository<Estimate, Long> {

    /**
     * Find all estimates for a specific chain
     */
    List<Estimate> findByChainId(Long chainId);

    /**
     * Find all estimates for a specific group
     */
    List<Estimate> findByGroupName(String groupName);

    /**
     * Find all estimates for a specific brand
     */
    List<Estimate> findByBrandName(String brandName);

    /**
     * Find all estimates for a specific zone
     */
    List<Estimate> findByZoneName(String zoneName);

    /**
     * Get total count of estimates
     */
    @Query("SELECT COUNT(e) FROM Estimate e")
    Long getTotalCount();

    /**
     * Get total estimated amount across all estimates
     */
    @Query("SELECT COALESCE(SUM(e.totalCost), 0) FROM Estimate e")
    Double getTotalEstimatedAmount();

    /**
     * Get count of estimates by chain ID
     */
    Long countByChainId(Long chainId);

    /**
     * Check if estimate exists for chain
     */
    boolean existsByChainId(Long chainId);
}
