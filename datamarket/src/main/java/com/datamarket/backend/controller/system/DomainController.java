package com.datamarket.backend.controller.system;

import com.datamarket.backend.dto.response.ApiResponse;
import com.datamarket.backend.dto.response.datasetResponse.DomainSummaryResponse;
import com.datamarket.backend.mapper.DatasetDomainMapper;
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
    private final DatasetDomainMapper datasetDomainMapper;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<DomainSummaryResponse>>> getAllDomains() {
        String message = "Domains retrieved successfully";

        List<DomainSummaryResponse> domains = datasetDomainMapper.toDomainSummary(datasetDomainService.getAllDomains());

        if(domains.isEmpty()) {
            message = "No domains found";
        }
        ApiResponse<List<DomainSummaryResponse>> response = ApiResponse.<List<DomainSummaryResponse>>builder()
                .data(domains)
                .message(message)
                .build();
        return ResponseEntity.ok(response);
    }
}
