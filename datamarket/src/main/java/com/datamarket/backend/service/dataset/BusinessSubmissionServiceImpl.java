package com.datamarket.backend.service.dataset;

import com.datamarket.backend.entity.Provider;
import com.datamarket.backend.entity.User;
import com.datamarket.backend.entity.dataset.BusinessSubmission;
import com.datamarket.backend.entity.dataset.DatasetClassification;
import com.datamarket.backend.entity.dataset.DatasetVersion;
import com.datamarket.backend.entity.dataset.ProviderDeclaration;
import com.datamarket.backend.enums.BusinessSubmissionStatus;
import com.datamarket.backend.enums.DatasetLevel;
import com.datamarket.backend.enums.DatasetVersionStatus;
import com.datamarket.backend.enums.ProviderStatus;
import com.datamarket.backend.exception.CustomException;
import com.datamarket.backend.exception.ErrorCode;
import com.datamarket.backend.repository.dataset.BusinessSubmissionRepository;
import com.datamarket.backend.security.util.SecurityUtil;
import com.datamarket.backend.service.provider.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessSubmissionServiceImpl implements BusinessSubmissionService {
    private final BusinessSubmissionRepository businessSubmissionRepository;
    private final ProviderService providerService;
    private final DatasetVersionService datasetVersionService;
    private final ProviderDeclarationService providerDeclarationService;
    private final DatasetClassificationService datasetClassificationService;


    @Override
    public BusinessSubmission submit(Long versionId) {
        User user = SecurityUtil.getCurrentUser();
        Provider provider = providerService.findById(user.getId());

        if (provider.getStatus() != ProviderStatus.APPROVED) {
            throw new CustomException(ErrorCode.PROVIDER_003);
        }

        DatasetVersion version = datasetVersionService.findById(versionId);

        if (!version.getDataset().getProvider().getId().equals(provider.getId())) {
            throw new CustomException(ErrorCode.DATASET_007);
        }

        if (version.getStatus() != DatasetVersionStatus.TECHNICAL_APPROVED) {
            throw new CustomException(ErrorCode.DATASET_031);
        }

        DatasetClassification classification = datasetClassificationService.getByVersionId(versionId);
        ProviderDeclaration declaration = providerDeclarationService.getByVersionId(versionId);

        if(classification == null || declaration == null){
            throw new CustomException(ErrorCode.DATASET_064);
        }

        if (classification.getDatasetLevel() != DatasetLevel.RAW
                && classification.getDerivedFromVersion() == null) {

            if (declaration.getMethodologyExplanation() == null
                    || declaration.getLicenseStatement() == null) {
                throw new CustomException(ErrorCode.DATASET_063);
            }
        }

        BusinessSubmission submission =businessSubmissionRepository.save(
                BusinessSubmission.builder()
                        .datasetVersion(version)
                        .submittedBy(provider)
                        .submissionStatus(BusinessSubmissionStatus.SUBMITTED)
                        .build()
        );

        version.setStatus(DatasetVersionStatus.BUSINESS_REVIEW);
        datasetVersionService.save(version);
        return submission;
     }

    @Override
    public List<BusinessSubmission> getAll() {
        return businessSubmissionRepository.findByDatasetVersion_Status(DatasetVersionStatus.BUSINESS_REVIEW);
    }

    @Override
    public BusinessSubmission getById(Long id) {
        return businessSubmissionRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.DATASET_067));
    }
}
