package com.internship.estimatemanagement.controller;

import com.internship.estimatemanagement.dto.DashboardStats;
import com.internship.estimatemanagement.dto.EstimateDTO;
import com.internship.estimatemanagement.dto.EstimateRequest;
import com.internship.estimatemanagement.service.EstimateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for Estimate Management operations
 * Provides both API endpoints (JSON) and Thymeleaf views
 */
@Controller
@RequestMapping("/estimates")
public class EstimateController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EstimateController.class);

    private final EstimateService estimateService;

    public EstimateController(EstimateService estimateService) {
        this.estimateService = estimateService;
    }

    // ==================== THYMELEAF VIEW ENDPOINTS ====================

    /**
     * Dashboard - Main page with statistics
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        log.info("Loading dashboard");
        DashboardStats stats = estimateService.getDashboardStats();
        List<EstimateDTO> recentEstimates = estimateService.getAllEstimates();

        // Get last 10 estimates sorted by creation date
        if (recentEstimates.size() > 10) {
            recentEstimates = recentEstimates.subList(0, 10);
        }

        model.addAttribute("stats", stats);
        model.addAttribute("recentEstimates", recentEstimates);
        return "dashboard";
    }

    /**
     * List all estimates
     */
    @GetMapping("/list")
    public String listEstimates(Model model) {
        log.info("Listing all estimates");
        model.addAttribute("estimates", estimateService.getAllEstimates());
        return "estimate-list";
    }

    /**
     * Show create estimate form
     */
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        log.info("Showing create estimate form");
        model.addAttribute("estimateRequest", new EstimateRequest());
        return "estimate-form";
    }

    /**
     * Show edit estimate form
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        log.info("Showing edit form for estimate: {}", id);
        EstimateDTO estimate = estimateService.getEstimateById(id);
        model.addAttribute("estimate", estimate);
        return "estimate-edit";
    }

    /**
     * Handle estimate creation from form
     */
    @PostMapping("/create")
    public String createEstimate(@ModelAttribute EstimateRequest request, Model model) {
        log.info("Creating estimate via form");
        try {
            EstimateDTO created = estimateService.createEstimate(request);
            model.addAttribute("success", "Estimate created successfully!");
            model.addAttribute("estimate", created);
            return "redirect:/estimates/dashboard";
        } catch (Exception e) {
            log.error("Error creating estimate", e);
            model.addAttribute("error", "Error: " + e.getMessage());
            return "estimate-form";
        }
    }

    /**
     * Handle estimate update from form
     */
    @PostMapping("/update/{id}")
    public String updateEstimate(@PathVariable Long id, @ModelAttribute EstimateRequest request, Model model) {
        log.info("Updating estimate via form: {}", id);
        try {
            EstimateDTO updated = estimateService.updateEstimate(id, request);
            model.addAttribute("success", "Estimate updated successfully!");
            return "redirect:/estimates/dashboard";
        } catch (Exception e) {
            log.error("Error updating estimate", e);
            model.addAttribute("error", "Error: " + e.getMessage());
            model.addAttribute("estimate", estimateService.getEstimateById(id));
            return "estimate-edit";
        }
    }

    /**
     * Delete estimate with confirmation
     */
    @PostMapping("/delete/{id}")
    public String deleteEstimate(@PathVariable Long id, Model model) {
        log.info("Deleting estimate: {}", id);
        try {
            estimateService.deleteEstimate(id);
            return "redirect:/estimates/dashboard?deleted=true";
        } catch (Exception e) {
            log.error("Error deleting estimate", e);
            model.addAttribute("error", "Error: " + e.getMessage());
            return "redirect:/estimates/list";
        }
    }

    // ==================== API JSON ENDPOINTS ====================

    /**
     * GET - Get all estimates (JSON)
     */
    @GetMapping("/api/all")
    @ResponseBody
    public ResponseEntity<List<EstimateDTO>> getAllEstimatesApi() {
        log.info("API: Getting all estimates");
        return ResponseEntity.ok(estimateService.getAllEstimates());
    }

    /**
     * GET - Get estimate by ID (JSON)
     */
    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<EstimateDTO> getEstimateByIdApi(@PathVariable Long id) {
        log.info("API: Getting estimate by ID: {}", id);
        return ResponseEntity.ok(estimateService.getEstimateById(id));
    }

    /**
     * GET - Get estimates by chain ID (JSON)
     */
    @GetMapping("/api/chain/{chainId}")
    @ResponseBody
    public ResponseEntity<List<EstimateDTO>> getEstimatesByChainApi(@PathVariable Long chainId) {
        log.info("API: Getting estimates for chain: {}", chainId);
        return ResponseEntity.ok(estimateService.getEstimatesByChainId(chainId));
    }

    /**
     * GET - Search estimates (JSON)
     */
    @GetMapping("/api/search")
    @ResponseBody
    public ResponseEntity<List<EstimateDTO>> searchEstimatesApi(
            @RequestParam(required = false) String groupName,
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String zoneName) {
        log.info("API: Searching estimates");
        return ResponseEntity.ok(estimateService.searchEstimates(groupName, brandName, zoneName));
    }

    /**
     * GET - Dashboard stats (JSON)
     */
    @GetMapping("/api/stats")
    @ResponseBody
    public ResponseEntity<DashboardStats> getDashboardStatsApi() {
        log.info("API: Getting dashboard stats");
        return ResponseEntity.ok(estimateService.getDashboardStats());
    }

    /**
     * POST - Create estimate (JSON)
     */
    @PostMapping("/api/create")
    @ResponseBody
    public ResponseEntity<EstimateDTO> createEstimateApi(@Valid @RequestBody EstimateRequest request) {
        log.info("API: Creating estimate");
        EstimateDTO created = estimateService.createEstimate(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT - Update estimate (JSON)
     */
    @PutMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<EstimateDTO> updateEstimateApi(
            @PathVariable Long id,
            @Valid @RequestBody EstimateRequest request) {
        log.info("API: Updating estimate: {}", id);
        return ResponseEntity.ok(estimateService.updateEstimate(id, request));
    }

    /**
     * DELETE - Delete estimate (JSON)
     */
    @DeleteMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteEstimateApi(@PathVariable Long id) {
        log.info("API: Deleting estimate: {}", id);
        estimateService.deleteEstimate(id);
        return ResponseEntity.ok(Map.of("message", "Estimate deleted successfully"));
    }
}
