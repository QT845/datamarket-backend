package com.datamarket.backend.service.dataset;

import com.datamarket.backend.dto.response.VersionResponse;
import com.datamarket.backend.entity.dataset.DatasetVersion;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DatasetVersionService {
    VersionResponse createDatasetVersion(Long datasetId, MultipartFile file) ;
    DatasetVersion findById(Long versionId);
    DatasetVersion save(DatasetVersion datasetVersion);
}
