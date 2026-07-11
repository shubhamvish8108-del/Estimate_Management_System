package com.internship.estimatemanagement.repository;

import com.internship.estimatemanagement.entity.Estimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstimateRepository extends JpaRepository<Estimate, Long> {

   
    List<Estimate> findByChainId(Long chainId);

  
    List<Estimate> findByGroupName(String groupName);

    List<Estimate> findByBrandName(String brandName);


    List<Estimate> findByZoneName(String zoneName);

  
    @Query("SELECT COUNT(e) FROM Estimate e")
    Long getTotalCount();

    @Query("SELECT COALESCE(SUM(e.totalCost), 0) FROM Estimate e")
    Double getTotalEstimatedAmount();

  
    Long countByChainId(Long chainId);


    boolean existsByChainId(Long chainId);
}
