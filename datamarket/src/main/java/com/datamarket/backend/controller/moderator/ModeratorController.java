package com.datamarket.backend.controller.moderator;

import com.datamarket.backend.dto.response.ApiResponse;
import com.datamarket.backend.dto.response.datasetResponse.DatasetQualityReportResponse;
import com.datamarket.backend.dto.response.ModReviewResponse;
import com.datamarket.backend.mapper.DatasetQualityReportMapper;
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
    private final DatasetQualityReportMapper datasetQualityReportMapper;

    @GetMapping("/dataset-quality-reports")
    public ResponseEntity<ApiResponse<List<DatasetQualityReportResponse>>> getAllDatasetQualityReports() {
        String message = "Dataset quality reports retrieved successfully";

        List<DatasetQualityReportResponse> responses = datasetQualityReportMapper.toQualityReports(datasetValidationService.getAll());

        if(responses.isEmpty()) {
            message = "No dataset quality reports found";
        }

        ApiResponse<List<DatasetQualityReportResponse>> apiResponse = new ApiResponse<>(true, message, responses);

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/dataset-quality-reports/{id}")
    public ResponseEntity<ApiResponse<DatasetQualityReportResponse>> getDatasetQualityReport(@PathVariable Long id) {
        DatasetQualityReportResponse response = datasetQualityReportMapper.toQualityReport(datasetValidationService.getById(id));
        ApiResponse<DatasetQualityReportResponse> apiResponse = new ApiResponse<>(true, "Dataset quality report retrieved successfully", response);
        return ResponseEntity.ok(apiResponse);
    }

    // Fix to mapper later
    @PostMapping("/versions/{versionId}/approve")
    public ResponseEntity<ApiResponse<ModReviewResponse>> approveDataset(@PathVariable Long versionId) {
        ModReviewResponse response = moderatorService.approveDataset(versionId);
        ApiResponse<ModReviewResponse> apiResponse = new ApiResponse<>(true, "Dataset version approved successfully", response);
        return ResponseEntity.ok(apiResponse);
    }

    // Fix to mapper later
    @PostMapping("/versions/{versionId}/reject")
    public ResponseEntity<ApiResponse<ModReviewResponse>> rejectDataset(@PathVariable Long versionId) {
        ModReviewResponse response = moderatorService.rejectDataset(versionId, "Does not meet quality standards");
        ApiResponse<ModReviewResponse> apiResponse = new ApiResponse<>(true, "Dataset version rejected successfully", response);
        return ResponseEntity.ok(apiResponse);
    }
}
