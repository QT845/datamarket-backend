package com.datamarket.backend.controller.moderator;

import com.datamarket.backend.dto.response.ApiResponse;
import com.datamarket.backend.dto.response.datasetResponse.DatasetQualityReportResponse;
import com.datamarket.backend.dto.response.ModReviewResponse;
import com.datamarket.backend.dto.response.datasetResponse.DatasetQualityReportSummaryResponse;
import com.datamarket.backend.dto.response.datasetResponse.SubmissionReportResponse;
import com.datamarket.backend.dto.response.datasetResponse.SubmissionReportSummaryResponse;
import com.datamarket.backend.entity.dataset.BusinessSubmission;
import com.datamarket.backend.entity.dataset.DatasetClassification;
import com.datamarket.backend.entity.dataset.ProviderDeclaration;
import com.datamarket.backend.mapper.BusinessSubmissionMapper;
import com.datamarket.backend.mapper.DatasetQualityReportMapper;
import com.datamarket.backend.service.dataset.BusinessSubmissionService;
import com.datamarket.backend.service.dataset.DatasetClassificationService;
import com.datamarket.backend.service.dataset.DatasetValidationService;
import com.datamarket.backend.service.dataset.ProviderDeclarationService;
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
    private final BusinessSubmissionService businessSubmissionService;
    private final ProviderDeclarationService providerDeclarationService;
    private final DatasetClassificationService datasetClassificationService;
    private final DatasetQualityReportMapper datasetQualityReportMapper;
    private final BusinessSubmissionMapper businessSubmissionMapper;

    @GetMapping("/dataset-quality-reports")
    public ResponseEntity<ApiResponse<List<DatasetQualityReportSummaryResponse>>> getAllDatasetQualityReports() {
        String message = "Dataset quality reports retrieved successfully";

        List<DatasetQualityReportSummaryResponse> responses = datasetQualityReportMapper.toQualityReports(datasetValidationService.getAll());

        if(responses.isEmpty()) {
            message = "No dataset quality reports found";
        }

        ApiResponse<List<DatasetQualityReportSummaryResponse>> apiResponse = new ApiResponse<>(true, message, responses);

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/dataset-quality-reports/{id}")
    public ResponseEntity<ApiResponse<DatasetQualityReportResponse>> getDatasetQualityReport(@PathVariable Long id) {
        DatasetQualityReportResponse response = datasetQualityReportMapper.toQualityReport(datasetValidationService.getById(id));
        ApiResponse<DatasetQualityReportResponse> apiResponse = new ApiResponse<>(true, "Dataset quality report retrieved successfully", response);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/versions/{versionId}/technical/approve")
    public ResponseEntity<ApiResponse<ModReviewResponse>> approveTechnical(@PathVariable Long versionId) {
        ModReviewResponse response = moderatorService.approveTechnical(versionId);
        ApiResponse<ModReviewResponse> apiResponse = new ApiResponse<>(true, "Technical review approved successfully", response);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/versions/{versionId}/technical/reject")
    public ResponseEntity<ApiResponse<ModReviewResponse>> rejectTechnical(@PathVariable Long versionId) {
        ModReviewResponse response = moderatorService.rejectTechnical(versionId, "Does not meet quality standards");
        ApiResponse<ModReviewResponse> apiResponse = new ApiResponse<>(true, "Technical review rejected successfully", response);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/submission-reports")
    public ResponseEntity<ApiResponse<List<SubmissionReportSummaryResponse>>> getAllBusinessSubmissions() {
        String message = "Submission reports retrieved successfully";
        List<SubmissionReportSummaryResponse> response = businessSubmissionMapper.toSubmissionReports(businessSubmissionService.getAll());
        if(response.isEmpty()) {
            message = "No submission reports found";
        }
        ApiResponse<List<SubmissionReportSummaryResponse>> apiResponse = new ApiResponse<>(true, message, response);

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/submission-reports/{id}")
    public ResponseEntity<ApiResponse<SubmissionReportResponse>> getSubmissionReport(@PathVariable Long id) {
        BusinessSubmission submit = businessSubmissionService.getById(id);

        ProviderDeclaration declaration = providerDeclarationService.findByDatasetVersionId(submit.getDatasetVersion().getId());

        DatasetClassification classification = datasetClassificationService.findByVersionId(submit.getDatasetVersion().getId());

        SubmissionReportResponse response = businessSubmissionMapper.toSubmissionReport(submit, declaration, classification);

        ApiResponse<SubmissionReportResponse> apiResponse = new ApiResponse<>(true, "Submission report retrieved successfully", response);

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/versions/{versionId}/business/approve")
    public ResponseEntity<ApiResponse<ModReviewResponse>> approveBusiness(@PathVariable Long versionId) {
        ModReviewResponse response = moderatorService.approveBusiness(versionId);
        ApiResponse<ModReviewResponse> apiResponse = new ApiResponse<>(true, "Business review approved successfully", response);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/versions/{versionId}/business/reject")
    public ResponseEntity<ApiResponse<ModReviewResponse>> rejectBusiness(@PathVariable Long versionId) {
        ModReviewResponse response = moderatorService.rejectBusiness(versionId, "Does not meet quality standards");
        ApiResponse<ModReviewResponse> apiResponse = new ApiResponse<>(true, "Business review rejected successfully", response);
        return ResponseEntity.ok(apiResponse);
    }
}
