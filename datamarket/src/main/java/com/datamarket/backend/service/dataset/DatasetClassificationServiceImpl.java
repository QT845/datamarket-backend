package com.datamarket.backend.service.dataset;

import com.datamarket.backend.dto.request.ClassificationRequest;
import com.datamarket.backend.entity.Provider;
import com.datamarket.backend.entity.User;
import com.datamarket.backend.entity.dataset.DatasetClassification;
import com.datamarket.backend.entity.dataset.DatasetVersion;
import com.datamarket.backend.enums.DatasetLevel;
import com.datamarket.backend.enums.DatasetVersionStatus;
import com.datamarket.backend.enums.ProviderStatus;
import com.datamarket.backend.exception.CustomException;
import com.datamarket.backend.exception.ErrorCode;
import com.datamarket.backend.repository.dataset.DatasetClassificationRepository;
import com.datamarket.backend.security.util.SecurityUtil;
import com.datamarket.backend.service.provider.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatasetClassificationServiceImpl implements  DatasetClassificationService {
    private final DatasetClassificationRepository datasetClassificationRepository;
    private final ProviderService providerService;
    private final DatasetVersionService datasetVersionService;

    @Override
    public DatasetClassification classify(Long versionId, ClassificationRequest request) {

        User user = SecurityUtil.getCurrentUser();
        Provider provider = providerService.findById(user.getId());

        if (provider.getStatus() != ProviderStatus.APPROVED) {
            throw new CustomException(ErrorCode.PROVIDER_003);
        }

        DatasetVersion version = datasetVersionService.findById(versionId);

        if (!version.getDataset().getProvider().getId().equals(provider.getId())) {
            throw new CustomException(ErrorCode.DATASET_007);
        }

        if (version.getStatus() == DatasetVersionStatus.BUSINESS_REVIEW) {
            throw new CustomException(ErrorCode.DATASET_065);
        }

        if (version.getStatus() != DatasetVersionStatus.TECHNICAL_APPROVED) {
            throw new CustomException(ErrorCode.DATASET_031);
        }


        DatasetClassification classification =
                datasetClassificationRepository.findByDatasetVersionId(versionId)
                        .orElse(DatasetClassification.builder()
                                .datasetVersion(version)
                                .build());

        classification.setDatasetLevel(request.getDatasetLevel());

        if (request.getDatasetLevel() == DatasetLevel.RAW) {
            classification.setDerivedFromVersion(null);
        } else {
            if (request.getDerivedFromVersionId() != null) {
                DatasetVersion sourceVersion =
                        datasetVersionService.findById(
                                request.getDerivedFromVersionId());
                classification.setDerivedFromVersion(sourceVersion);
            } else {
                classification.setDerivedFromVersion(null);
            }
        }

        return datasetClassificationRepository.save(classification);

    }

    @Override
    public DatasetClassification findByVersionId(Long versionId) {
        return datasetClassificationRepository.findByDatasetVersionId(versionId)
                .orElseThrow(() -> new CustomException(ErrorCode.DATASET_063));
    }

    @Override
    public DatasetClassification getByVersionId(Long versionId) {
        return datasetClassificationRepository.findByDatasetVersionId(versionId)
                .orElse(null);    }
}
