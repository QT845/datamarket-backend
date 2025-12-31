package com.datamarket.backend.controller.moderator;

import com.datamarket.backend.dto.response.ApiResponse;
import com.datamarket.backend.dto.response.DatasetQualityReportResponse;
import com.datamarket.backend.dto.response.ModReviewResponse;
import com.datamarket.backend.service.dataset.DatasetValidationService;
import com.datamarket.backend.service.moderator.ModeratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moderator")
@RequiredArgsConstructor
public class ModeratorController {
    private final ModeratorService moderatorService;
    private final DatasetValidationService datasetValidationService;

    @GetMapping("/dataset-quality-reports")
    public ResponseEntity<ApiResponse<List<DatasetQualityReportResponse>>> getAllDatasetQualityReports() {
        List<DatasetQualityReportResponse> responses = datasetValidationService.getAll();
        ApiResponse<List<DatasetQualityReportResponse>> apiResponse = new ApiResponse<>(true, "Dataset quality reports retrieved successfully", responses);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/dataset-quality-reports/{id}")
    public ResponseEntity<ApiResponse<DatasetQualityReportResponse>> getDatasetQualityReport(@PathVariable Long id) {
        DatasetQualityReportResponse response = datasetValidationService.getById(id);
        ApiResponse<DatasetQualityReportResponse> apiResponse = new ApiResponse<>(true, "Dataset quality report retrieved successfully", response);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/versions/{versionId}/approve")
    public ResponseEntity<ApiResponse<ModReviewResponse>> approveDataset(@PathVariable Long versionId) {
        ModReviewResponse response = moderatorService.approveDataset(versionId);
        ApiResponse<ModReviewResponse> apiResponse = new ApiResponse<>(true, "Dataset version approved successfully", response);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/versions/{versionId}/reject")
    public ResponseEntity<ApiResponse<ModReviewResponse>> rejectDataset(@PathVariable Long versionId) {
        ModReviewResponse response = moderatorService.rejectDataset(versionId, "Does not meet quality standards");
        ApiResponse<ModReviewResponse> apiResponse = new ApiResponse<>(true, "Dataset version rejected successfully", response);
        return ResponseEntity.ok(apiResponse);
    }
}
