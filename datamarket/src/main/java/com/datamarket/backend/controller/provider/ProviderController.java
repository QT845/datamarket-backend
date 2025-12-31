package com.datamarket.backend.controller.provider;

import com.datamarket.backend.dto.request.CreateDatasetRequest;
import com.datamarket.backend.dto.response.ApiResponse;
import com.datamarket.backend.dto.response.DatasetResponse;
import com.datamarket.backend.dto.response.VersionResponse;
import com.datamarket.backend.service.dataset.DatasetService;
import com.datamarket.backend.service.dataset.DatasetVersionService;
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

    @PostMapping("/datasets/create")
    public ResponseEntity<ApiResponse<DatasetResponse>> createDataset(@RequestBody @Valid CreateDatasetRequest request) {
        DatasetResponse response = datasetService.createDataset(request);
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
}
