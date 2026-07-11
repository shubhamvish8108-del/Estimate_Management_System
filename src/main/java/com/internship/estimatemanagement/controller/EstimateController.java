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

 
    @GetMapping("/list")
    public String listEstimates(Model model) {
        log.info("Listing all estimates");
        model.addAttribute("estimates", estimateService.getAllEstimates());
        return "estimate-list";
    }

 
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        log.info("Showing create estimate form");
        model.addAttribute("estimateRequest", new EstimateRequest());
        return "estimate-form";
    }

   
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        log.info("Showing edit form for estimate: {}", id);
        EstimateDTO estimate = estimateService.getEstimateById(id);
        model.addAttribute("estimate", estimate);
        return "estimate-edit";
    }

   
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

 

    @GetMapping("/api/all")
    @ResponseBody
    public ResponseEntity<List<EstimateDTO>> getAllEstimatesApi() {
        log.info("API: Getting all estimates");
        return ResponseEntity.ok(estimateService.getAllEstimates());
    }

   
    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<EstimateDTO> getEstimateByIdApi(@PathVariable Long id) {
        log.info("API: Getting estimate by ID: {}", id);
        return ResponseEntity.ok(estimateService.getEstimateById(id));
    }

  
    @GetMapping("/api/chain/{chainId}")
    @ResponseBody
    public ResponseEntity<List<EstimateDTO>> getEstimatesByChainApi(@PathVariable Long chainId) {
        log.info("API: Getting estimates for chain: {}", chainId);
        return ResponseEntity.ok(estimateService.getEstimatesByChainId(chainId));
    }


    @GetMapping("/api/search")
    @ResponseBody
    public ResponseEntity<List<EstimateDTO>> searchEstimatesApi(
            @RequestParam(required = false) String groupName,
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String zoneName) {
        log.info("API: Searching estimates");
        return ResponseEntity.ok(estimateService.searchEstimates(groupName, brandName, zoneName));
    }

    @GetMapping("/api/stats")
    @ResponseBody
    public ResponseEntity<DashboardStats> getDashboardStatsApi() {
        log.info("API: Getting dashboard stats");
        return ResponseEntity.ok(estimateService.getDashboardStats());
    }


    @PostMapping("/api/create")
    @ResponseBody
    public ResponseEntity<EstimateDTO> createEstimateApi(@Valid @RequestBody EstimateRequest request) {
        log.info("API: Creating estimate");
        EstimateDTO created = estimateService.createEstimate(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

   
    @PutMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<EstimateDTO> updateEstimateApi(
            @PathVariable Long id,
            @Valid @RequestBody EstimateRequest request) {
        log.info("API: Updating estimate: {}", id);
        return ResponseEntity.ok(estimateService.updateEstimate(id, request));
    }

    @DeleteMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteEstimateApi(@PathVariable Long id) {
        log.info("API: Deleting estimate: {}", id);
        estimateService.deleteEstimate(id);
        return ResponseEntity.ok(Map.of("message", "Estimate deleted successfully"));
    }
}
