package com.datamarket.backend.service.dataset;

import com.datamarket.backend.dto.response.VersionResponse;
import com.datamarket.backend.entity.dataset.Dataset;
import com.datamarket.backend.entity.dataset.DatasetVersion;
import com.datamarket.backend.enums.DatasetGenerationType;
import com.datamarket.backend.enums.DatasetStatus;
import com.datamarket.backend.enums.DatasetVersionStatus;
import com.datamarket.backend.exception.CustomException;
import com.datamarket.backend.exception.ErrorCode;
import com.datamarket.backend.repository.dataset.DatasetVersionRepository;
import com.datamarket.backend.service.storage.StorageService;
import com.datamarket.backend.utils.CheckSumUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DatasetVersionServiceImpl implements DatasetVersionService{
    private final DatasetService datasetService;
    private final DatasetVersionRepository datasetVersionRepository;
    private final StorageService storageService;

    @Override
    public VersionResponse createDatasetVersion(Long datasetId, MultipartFile file) {
        Dataset dataset = datasetService.findById(datasetId);
        if(dataset.getStatus().equals(DatasetStatus.DELETED)
        || dataset.getStatus().equals(DatasetStatus.DRAFT)
        || dataset.getStatus().equals(DatasetStatus.REJECTED)) {
            throw new CustomException(ErrorCode.DATASET_006);
        }

        if (file == null || file.isEmpty()) {
            throw new CustomException(ErrorCode.COMMON_015);
        }

        String checksum;
        try {
            checksum = CheckSumUtil.sha256(file.getInputStream());
        } catch (IOException e) {
            throw new CustomException(ErrorCode.COMMON_016);
        }

        String fileName = file.getOriginalFilename();

        DatasetVersion datasetVersion = DatasetVersion.builder()
                .dataset(dataset)
                .version("v1.0")
                .status(DatasetVersionStatus.CREATED)
                .generationType(DatasetGenerationType.PROVIDER)
                .originFileName(fileName)
                .checksum(checksum)
                .build();
        datasetVersionRepository.save(datasetVersion);

        String objectKey = "datasets/" + dataset.getId()
                + "/" + datasetVersion.getId()
                + "/raw";
        try {
            datasetVersion.setDataLocation(storageService.save(file, objectKey));

        } catch (IOException e) {
            throw  new CustomException(ErrorCode.COMMON_017);
        }
        datasetVersionRepository.save(datasetVersion);

        return VersionResponse.builder()
                .id(datasetVersion.getId())
                .datasetId(datasetVersion.getDataset().getId())
                .version(datasetVersion.getVersion())
                .status(datasetVersion.getStatus())
                .generationType(datasetVersion.getGenerationType())
                .dataLocation(datasetVersion.getDataLocation())
                .checksum(datasetVersion.getChecksum())
                .originFileName(datasetVersion.getOriginFileName())
                .createdAt(datasetVersion.getCreatedAt())
                .build();
    }
}
