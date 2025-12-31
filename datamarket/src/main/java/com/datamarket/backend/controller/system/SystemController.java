package com.datamarket.backend.controller.system;

import com.datamarket.backend.dto.response.ApiResponse;
import com.datamarket.backend.dto.response.DatasetQualityReportResponse;
import com.datamarket.backend.service.dataset.DatasetValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system")
@RequiredArgsConstructor
public class SystemController {
    private final DatasetValidationService datasetValidationService;

    @PostMapping("/versions/{versionId}/validate")
    public ResponseEntity<ApiResponse<DatasetQualityReportResponse>> validateDatasetVersion(@PathVariable Long versionId) {
        DatasetQualityReportResponse response = datasetValidationService.validateDatasetVersion(versionId);
        ApiResponse<DatasetQualityReportResponse> apiResponse = new ApiResponse<>(true, "Dataset version validated successfully", response);
        return ResponseEntity.ok(apiResponse);
    }
}
