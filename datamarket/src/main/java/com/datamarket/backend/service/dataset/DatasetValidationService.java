package com.datamarket.backend.service.dataset;

import com.datamarket.backend.entity.dataset.DatasetQualityReport;

import java.util.List;

public interface DatasetValidationService {
    DatasetQualityReport validateDatasetVersion(Long versionId);
    List<DatasetQualityReport> getAll();
    DatasetQualityReport getById(Long id);

}
