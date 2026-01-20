package com.datamarket.backend.service.dataset;

import com.datamarket.backend.dto.request.CreateDatasetRequest;
import com.datamarket.backend.dto.request.UpdateDatasetRequest;
import com.datamarket.backend.dto.response.datasetResponse.DatasetResponse;
import com.datamarket.backend.entity.Provider;
import com.datamarket.backend.entity.User;
import com.datamarket.backend.entity.dataset.Dataset;
import com.datamarket.backend.entity.dataset.DatasetVersion;
import com.datamarket.backend.enums.*;
import com.datamarket.backend.exception.CustomException;
import com.datamarket.backend.exception.ErrorCode;
import com.datamarket.backend.repository.dataset.DatasetRepository;
import com.datamarket.backend.security.util.SecurityUtil;
import com.datamarket.backend.service.provider.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatasetServiceImpl implements DatasetService {
    private final ProviderService providerService;
    private final DatasetRepository datasetRepository;
    private final DatasetDomainService datasetDomainService;
    private final DatasetVersionService datasetVersionService;

    @Override
    public Dataset createDataset(CreateDatasetRequest request) {
        User user = SecurityUtil.getCurrentUser();
        Provider provider = providerService.findById(user.getId());
        if(!provider.getStatus().equals(ProviderStatus.APPROVED)) {
            throw new CustomException(ErrorCode.PROVIDER_003);
        }

        Dataset dataset = Dataset.builder()
                .name(request.getName())
                .description(request.getDescription())
                .domain(datasetDomainService.findById(request.getDomainId()))
                .status(DatasetStatus.DRAFT)
                .provider(provider)
                .currentVersion(null)
                .build();

        return datasetRepository.save(dataset);
    }

    @Override
    public Dataset updateDataset(Long datasetId, UpdateDatasetRequest request) {
        User user = SecurityUtil.getCurrentUser();
        Provider provider = providerService.findById(user.getId());
        if(!provider.getStatus().equals(ProviderStatus.APPROVED)) {
            throw new CustomException(ErrorCode.PROVIDER_003);
        }

        Dataset dataset = datasetRepository.findById(datasetId)
                .orElseThrow(() -> new CustomException(ErrorCode.DATASET_001));

        if (!dataset.getProvider().getId().equals(provider.getId())) {
            throw new CustomException(ErrorCode.DATASET_007);
        }

        dataset.setName(request.getName());
        dataset.setDescription(request.getDescription());


        return datasetRepository.save(dataset);
    }

    @Override
    public Dataset getDataset(Long datasetId) {
        return datasetRepository.findById(datasetId)
                .orElseThrow(() -> new CustomException(ErrorCode.DATASET_001));
    }

    @Override
    public void archiveDataset(Long datasetId) {
        User user = SecurityUtil.getCurrentUser();
        Provider provider = providerService.findById(user.getId());

        Dataset dataset = datasetRepository.findById(datasetId)
                .orElseThrow(() -> new CustomException(ErrorCode.DATASET_001));

        if (user.getRole() == RoleType.MODERATOR) {
            dataset.setStatus(DatasetStatus.REMOVED_BY_MOD);
        } else {
            if (!dataset.getProvider().getId().equals(provider.getId())) {
                throw new CustomException(ErrorCode.DATASET_007);
            }
            dataset.setStatus(DatasetStatus.ARCHIVED_BY_PROVIDER);
        }

        datasetRepository.save(dataset);
    }

    @Override
    public void switchCurrentVersion(Long datasetId, Long versionId) {
        User user = SecurityUtil.getCurrentUser();

        if (user.getRole() != RoleType.MODERATOR
                && user.getRole() != RoleType.ADMIN) {
            throw new CustomException(ErrorCode.AUTH_011);
        }

        Dataset dataset = datasetRepository.findById(datasetId)
                .orElseThrow(() -> new CustomException(ErrorCode.DATASET_001));

        if(dataset.getStatus().equals(DatasetStatus.DELETED)
        || dataset.getStatus().equals(DatasetStatus.ARCHIVED_BY_PROVIDER)
        || dataset.getStatus().equals(DatasetStatus.REMOVED_BY_MOD)) {
            throw new CustomException(ErrorCode.DATASET_006);
        }

        DatasetVersion version = datasetVersionService.findById(versionId);

        if(!version.getDataset().getId().equals(dataset.getId())) {
            throw new CustomException(ErrorCode.DATASET_028);
        }

        if (version.getStatus() != DatasetVersionStatus.APPROVED) {
            throw new CustomException(ErrorCode.DATASET_028);
        }


        dataset.setCurrentVersion(version);
        datasetRepository.save(dataset);

    }

    @Override
    public Dataset findById(Long id) {
        return datasetRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.DATASET_001));
    }

    @Override
    public Dataset save(Dataset dataset) {
        return datasetRepository.save(dataset);
    }


}
