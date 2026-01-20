package com.datamarket.backend.service.dataset;

import com.datamarket.backend.dto.response.datasetResponse.VersionResponse;
import com.datamarket.backend.entity.dataset.DatasetVersion;
import org.springframework.web.multipart.MultipartFile;

public interface DatasetVersionService {
    DatasetVersion createDatasetVersion(Long datasetId, MultipartFile file) ;
    DatasetVersion findById(Long versionId);
    DatasetVersion save(DatasetVersion datasetVersion);
}
