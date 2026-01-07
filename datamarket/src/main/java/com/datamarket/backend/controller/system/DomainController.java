package com.datamarket.backend.controller.system;

import com.datamarket.backend.dto.response.ApiResponse;
import com.datamarket.backend.dto.response.DomainSummaryResponse;
import com.datamarket.backend.service.dataset.DatasetDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/domains")
@RequiredArgsConstructor
public class DomainController {
    private final DatasetDomainService datasetDomainService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<DomainSummaryResponse>>> getAllDomains() {
        List<DomainSummaryResponse> domains = datasetDomainService.getAllDomains();
        ApiResponse<List<DomainSummaryResponse>> response = ApiResponse.<List<DomainSummaryResponse>>builder()
                .data(domains)
                .message("Domains retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}
