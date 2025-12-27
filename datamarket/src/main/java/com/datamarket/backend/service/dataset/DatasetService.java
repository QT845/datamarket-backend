package com.datamarket.backend.service.dataset;

import com.datamarket.backend.dto.request.CreateDatasetRequest;
import com.datamarket.backend.dto.request.UpdateDatasetRequest;
import com.datamarket.backend.dto.response.DatasetResponse;

public interface DatasetService {
    DatasetResponse createDataset(CreateDatasetRequest request);
    DatasetResponse updateDataset(Long datasetId, UpdateDatasetRequest request);
    DatasetResponse getDataset(Long datasetId);
    void archiveDataset(Long datasetId);
    void switchCurrentVersion(Long datasetId, Long versionId);
}
