package com.datamarket.backend.service.dataset;

import com.datamarket.backend.dto.response.DatasetQualityReportResponse;

import java.util.List;

public interface DatasetValidationService {
    DatasetQualityReportResponse validateDatasetVersion(Long versionId);
    List<DatasetQualityReportResponse> getAll();
    DatasetQualityReportResponse getById(Long id);

}
