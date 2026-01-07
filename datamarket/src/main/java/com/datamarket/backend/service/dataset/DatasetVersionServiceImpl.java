package com.datamarket.backend.service.dataset;

import com.datamarket.backend.dto.response.VersionResponse;
import com.datamarket.backend.entity.Provider;
import com.datamarket.backend.entity.User;
import com.datamarket.backend.entity.dataset.Dataset;
import com.datamarket.backend.entity.dataset.DatasetVersion;
import com.datamarket.backend.enums.DatasetGenerationType;
import com.datamarket.backend.enums.DatasetStatus;
import com.datamarket.backend.enums.DatasetVersionStatus;
import com.datamarket.backend.exception.CustomException;
import com.datamarket.backend.exception.ErrorCode;
import com.datamarket.backend.repository.dataset.DatasetRepository;
import com.datamarket.backend.repository.dataset.DatasetVersionRepository;
import com.datamarket.backend.security.util.SecurityUtil;
import com.datamarket.backend.service.provider.ProviderService;
import com.datamarket.backend.service.storage.StorageService;
import com.datamarket.backend.utils.CheckSumUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DatasetVersionServiceImpl implements DatasetVersionService{
    private final DatasetVersionRepository datasetVersionRepository;
    private final StorageService storageService;
    private final ProviderService providerService;
    private final DatasetRepository datasetRepository;

    @Override
    public VersionResponse createDatasetVersion(Long datasetId, MultipartFile file) {
        User user = SecurityUtil.getCurrentUser();
        Provider provider = providerService.findById(user.getId());

        Dataset dataset = datasetRepository.findById(datasetId).orElseThrow(
                () -> new CustomException(ErrorCode.DATASET_001));

        if (!dataset.getProvider().getId().equals(provider.getId())) {
            throw new CustomException(ErrorCode.DATASET_007);
        }
        if (dataset.getStatus() == DatasetStatus.DELETED
        || dataset.getStatus() == DatasetStatus.ARCHIVED_BY_PROVIDER
        || dataset.getStatus() == DatasetStatus.REMOVED_BY_MOD) {
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

        boolean exists = datasetVersionRepository.existsByDatasetAndChecksum(dataset, checksum);
        if (exists) {
            throw new CustomException(ErrorCode.DATASET_022);
        }

        String fileName = file.getOriginalFilename();

        DatasetVersion datasetVersion = DatasetVersion.builder()
                .dataset(dataset)
                .version("v1.0") // TODO Sprint 2: auto increment version (v1.1, v1.2...)
                .status(DatasetVersionStatus.CREATED)
                .generationType(DatasetGenerationType.PROVIDER)
                .originFileName(fileName)
                .dataLocation("")
                .checksum(checksum)
                .build();
        datasetVersionRepository.save(datasetVersion);

        String objectKey = "datasets/" + dataset.getId()
                + "/" + datasetVersion.getId()
                + "/raw.csv"; // TODO Sprint 2: support multiple data artifacts per version
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

    @Override
    public DatasetVersion findById(Long versionId) {
        return datasetVersionRepository.findById(versionId).orElseThrow(() -> new CustomException(ErrorCode.DATASET_020));
    }

    @Override
    public DatasetVersion save(DatasetVersion datasetVersion) {
        return datasetVersionRepository.save(datasetVersion);
    }
}
