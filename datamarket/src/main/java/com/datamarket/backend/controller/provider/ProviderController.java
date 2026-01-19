package com.datamarket.backend.controller.provider;

import com.datamarket.backend.dto.request.ClassificationRequest;
import com.datamarket.backend.dto.request.CreateDatasetRequest;
import com.datamarket.backend.dto.request.DeclarationRequest;
import com.datamarket.backend.dto.response.*;
import com.datamarket.backend.entity.dataset.BusinessSubmission;
import com.datamarket.backend.mapper.BusinessSubmissionMapper;
import com.datamarket.backend.mapper.DatasetClassificationMapper;
import com.datamarket.backend.mapper.DatasetMapper;
import com.datamarket.backend.mapper.ProviderDeclarationMapper;
import com.datamarket.backend.service.dataset.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/provider")
@RequiredArgsConstructor
public class ProviderController {
    private final DatasetService datasetService;
    private final DatasetVersionService datasetVersionService;
    private final ProviderDeclarationService providerDeclarationService;
    private final DatasetClassificationService datasetClassificationService;
    private final BusinessSubmissionService businessSubmissionService;
    private final DatasetMapper datasetMapper;
    private final ProviderDeclarationMapper providerDeclarationMapper;
    private final DatasetClassificationMapper datasetClassificationMapper;
    private final BusinessSubmissionMapper businessSubmissionMapper;

    @PostMapping("/datasets/create")
    public ResponseEntity<ApiResponse<DatasetResponse>> createDataset(@RequestBody @Valid CreateDatasetRequest request) {
        DatasetResponse response = datasetMapper.toDatasetResponse(datasetService.createDataset(request));
        ApiResponse<DatasetResponse> apiResponse = new ApiResponse<>(true, "Dataset created successfully", response);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/datasets/{datasetId}/versions/upload")
    public ResponseEntity<ApiResponse<VersionResponse>> uploadDatasetVersion(
            @PathVariable Long datasetId,
            @RequestParam("file") MultipartFile file) {
        VersionResponse response = datasetVersionService.createDatasetVersion(datasetId, file);
        ApiResponse<VersionResponse> apiResponse = new ApiResponse<>(true, "Dataset version uploaded successfully", response);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/versions/{versionId}/business-metadata")
    public ResponseEntity<ApiResponse<DeclarationResponse>> declare(
            @PathVariable Long versionId,
            @RequestBody @Valid DeclarationRequest request) {
        DeclarationResponse response = providerDeclarationMapper.toDeclarationResponse(providerDeclarationService.declare(versionId, request));
        ApiResponse<DeclarationResponse> apiResponse = new ApiResponse<>(true, "Declaration submitted successfully", response);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/versions/{versionId}/classification")
    public ResponseEntity<ApiResponse<ClassificationResponse>> classify(
            @PathVariable Long versionId,
            @RequestBody @Valid ClassificationRequest request) {
        ClassificationResponse response = datasetClassificationMapper.toClassification(datasetClassificationService.classify(versionId, request));
        ApiResponse<ClassificationResponse> apiResponse = new ApiResponse<>(true, "Dataset classified successfully", response);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/versions/{versionId}/submit")
    public ResponseEntity<ApiResponse<BusinessSubmissionResponse>> submit(@PathVariable Long versionId) {
        BusinessSubmissionResponse submission = businessSubmissionMapper.toResponse(businessSubmissionService.submit(versionId));
        ApiResponse<BusinessSubmissionResponse> apiResponse = new ApiResponse<>(true, "Business submission created successfully", submission);
        return ResponseEntity.ok(apiResponse);
    }
}
