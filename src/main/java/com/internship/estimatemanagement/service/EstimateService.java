package com.internship.estimatemanagement.service;

import com.internship.estimatemanagement.dto.DashboardStats;
import com.internship.estimatemanagement.dto.EstimateDTO;
import com.internship.estimatemanagement.dto.EstimateRequest;
import com.internship.estimatemanagement.entity.Estimate;
import com.internship.estimatemanagement.repository.EstimateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Estimate business logic
 */
@Service
public class EstimateService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EstimateService.class);

    private final EstimateRepository estimateRepository;

    public EstimateService(EstimateRepository estimateRepository) {
        this.estimateRepository = estimateRepository;
    }

    /**
     * Create a new estimate
     */
    @Transactional
    public EstimateDTO createEstimate(EstimateRequest request) {
        log.info("Creating new estimate for chain ID: {}", request.getChainId());

        Estimate estimate = Estimate.builder()
                .chainId(request.getChainId())
                .groupName(request.getGroupName())
                .brandName(request.getBrandName())
                .zoneName(request.getZoneName())
                .service(request.getService())
                .qty(request.getQty())
                .costPerUnit(request.getCostPerUnit())
                .deliveryDate(request.getDeliveryDate())
                .deliveryDetails(request.getDeliveryDetails())
                .build();

        // Calculate total cost
        if (estimate.getQty() != null && estimate.getCostPerUnit() != null) {
            estimate.setTotalCost(estimate.getQty() * estimate.getCostPerUnit());
        }

        Estimate saved = estimateRepository.save(estimate);
        log.info("Estimate created with ID: {}", saved.getEstimatedId());

        return toDTO(saved);
    }

    /**
     * Get estimate by ID
     */
    public EstimateDTO getEstimateById(Long id) {
        log.debug("Fetching estimate with ID: {}", id);
        Estimate estimate = estimateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estimate not found with ID: " + id));
        return toDTO(estimate);
    }

    /**
     * Get all estimates
     */
    public List<EstimateDTO> getAllEstimates() {
        log.debug("Fetching all estimates");
        return estimateRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get estimates by chain ID
     */
    public List<EstimateDTO> getEstimatesByChainId(Long chainId) {
        log.debug("Fetching estimates for chain ID: {}", chainId);
        return estimateRepository.findByChainId(chainId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing estimate
     */
    @Transactional
    public EstimateDTO updateEstimate(Long id, EstimateRequest request) {
        log.info("Updating estimate with ID: {}", id);

        Estimate estimate = estimateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estimate not found with ID: " + id));

        estimate.setChainId(request.getChainId());
        estimate.setGroupName(request.getGroupName());
        estimate.setBrandName(request.getBrandName());
        estimate.setZoneName(request.getZoneName());
        estimate.setService(request.getService());
        estimate.setQty(request.getQty());
        estimate.setCostPerUnit(request.getCostPerUnit());
        estimate.setDeliveryDate(request.getDeliveryDate());
        estimate.setDeliveryDetails(request.getDeliveryDetails());

        // Recalculate total cost
        if (estimate.getQty() != null && estimate.getCostPerUnit() != null) {
            estimate.setTotalCost(estimate.getQty() * estimate.getCostPerUnit());
        }

        Estimate updated = estimateRepository.save(estimate);
        log.info("Estimate updated: {}", updated.getEstimatedId());

        return toDTO(updated);
    }

    /**
     * Delete an estimate
     */
    @Transactional
    public void deleteEstimate(Long id) {
        log.info("Deleting estimate with ID: {}", id);

        if (!estimateRepository.existsById(id)) {
            throw new RuntimeException("Estimate not found with ID: " + id);
        }

        estimateRepository.deleteById(id);
        log.info("Estimate deleted: {}", id);
    }

    /**
     * Get dashboard statistics
     */
    public DashboardStats getDashboardStats() {
        Long totalEstimates = estimateRepository.getTotalCount();
        Double totalAmount = estimateRepository.getTotalEstimatedAmount();

        // Count estimates created this month
        LocalDateTime startOfMonth = YearMonth.now().atDay(1).atStartOfDay();
        LocalDateTime now = LocalDateTime.now();

        List<Estimate> thisMonthEstimates = estimateRepository.findAll().stream()
                .filter(e -> e.getCreatedAt() != null &&
                        e.getCreatedAt().isAfter(startOfMonth) &&
                        e.getCreatedAt().isBefore(now.plusDays(1)))
                .toList();

        // Count unique chains
        long uniqueChains = estimateRepository.findAll().stream()
                .map(Estimate::getChainId)
                .distinct()
                .count();

        return DashboardStats.builder()
                .totalEstimates(totalEstimates != null ? totalEstimates : 0L)
                .totalEstimatedAmount(totalAmount != null ? totalAmount : 0.0)
                .estimatesThisMonth((long) thisMonthEstimates.size())
                .uniqueChains(uniqueChains)
                .build();
    }

    /**
     * Search estimates by various criteria
     */
    public List<EstimateDTO> searchEstimates(String groupName, String brandName, String zoneName) {
        log.debug("Searching estimates - group: {}, brand: {}, zone: {}", groupName, brandName, zoneName);

        List<Estimate> allEstimates = estimateRepository.findAll();

        return allEstimates.stream()
                .filter(e -> groupName == null || groupName.isEmpty() ||
                        (e.getGroupName() != null && e.getGroupName().toLowerCase().contains(groupName.toLowerCase())))
                .filter(e -> brandName == null || brandName.isEmpty() ||
                        (e.getBrandName() != null && e.getBrandName().toLowerCase().contains(brandName.toLowerCase())))
                .filter(e -> zoneName == null || zoneName.isEmpty() ||
                        (e.getZoneName() != null && e.getZoneName().toLowerCase().contains(zoneName.toLowerCase())))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert Entity to DTO
     */
    private EstimateDTO toDTO(Estimate estimate) {
        return EstimateDTO.builder()
                .estimatedId(estimate.getEstimatedId())
                .chainId(estimate.getChainId())
                .groupName(estimate.getGroupName())
                .brandName(estimate.getBrandName())
                .zoneName(estimate.getZoneName())
                .service(estimate.getService())
                .qty(estimate.getQty())
                .costPerUnit(estimate.getCostPerUnit())
                .totalCost(estimate.getTotalCost())
                .deliveryDate(estimate.getDeliveryDate())
                .deliveryDetails(estimate.getDeliveryDetails())
                .createdAt(estimate.getCreatedAt())
                .updatedAt(estimate.getUpdatedAt())
                .build();
    }
}
