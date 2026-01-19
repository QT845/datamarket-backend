package com.datamarket.backend.service.dataset;

import com.datamarket.backend.dto.request.ClassificationRequest;
import com.datamarket.backend.entity.dataset.DatasetClassification;

public interface DatasetClassificationService {
    DatasetClassification classify(Long versionId, ClassificationRequest request);
    DatasetClassification findByVersionId(Long versionId);
    DatasetClassification getByVersionId(Long versionId);
}
