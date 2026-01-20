package com.datamarket.backend.service.dataset;

import com.datamarket.backend.dto.request.CreateDatasetRequest;
import com.datamarket.backend.dto.request.UpdateDatasetRequest;
import com.datamarket.backend.entity.dataset.Dataset;

public interface DatasetService {
    Dataset createDataset(CreateDatasetRequest request);
    Dataset updateDataset(Long datasetId, UpdateDatasetRequest request);
    Dataset getDataset(Long datasetId);
    void archiveDataset(Long datasetId);
    void switchCurrentVersion(Long datasetId, Long versionId);
    Dataset findById(Long id);
    Dataset save(Dataset dataset);
}
