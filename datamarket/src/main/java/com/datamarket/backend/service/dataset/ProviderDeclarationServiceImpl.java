package com.datamarket.backend.service.dataset;

import com.datamarket.backend.dto.request.DeclarationRequest;
import com.datamarket.backend.entity.Provider;
import com.datamarket.backend.entity.User;
import com.datamarket.backend.entity.dataset.DatasetVersion;
import com.datamarket.backend.entity.dataset.ProviderDeclaration;
import com.datamarket.backend.enums.DatasetVersionStatus;
import com.datamarket.backend.enums.ProviderStatus;
import com.datamarket.backend.exception.CustomException;
import com.datamarket.backend.exception.ErrorCode;
import com.datamarket.backend.repository.dataset.ProviderDeclarationRepository;
import com.datamarket.backend.security.util.SecurityUtil;
import com.datamarket.backend.service.provider.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProviderDeclarationServiceImpl implements ProviderDeclarationService {

    private final ProviderDeclarationRepository providerDeclarationRepository;
    private final ProviderService providerService;
    private final DatasetVersionService datasetVersionService;

    @Override
    public ProviderDeclaration declare(Long versionId, DeclarationRequest request) {

        User user = SecurityUtil.getCurrentUser();
        Provider provider = providerService.findById(user.getId());

        if (provider.getStatus() != ProviderStatus.APPROVED) {
            throw new CustomException(ErrorCode.PROVIDER_003);
        }

        DatasetVersion version = datasetVersionService.findById(versionId);

        if (!version.getDataset().getProvider().getId().equals(provider.getId())) {
            throw new CustomException(ErrorCode.DATASET_007);
        }

        if(version.getStatus() == DatasetVersionStatus.BUSINESS_REVIEW){
            throw new CustomException(ErrorCode.DATASET_065);
        }

        if (version.getStatus() != DatasetVersionStatus.TECHNICAL_APPROVED) {
            throw new CustomException(ErrorCode.DATASET_031);
        }

        if(request.getTimeRangeEnd().isBefore(request.getTimeRangeStart())){
                throw new CustomException(ErrorCode.DATASET_064);
        }

        ProviderDeclaration declaration =
                providerDeclarationRepository.findByDatasetVersionId(versionId)
                        .orElse(ProviderDeclaration.builder()
                                .datasetVersion(version)
                                .build());

        declaration.setDataSource(request.getDataSource());
        declaration.setCollectionMethod(request.getCollectionMethod());
        declaration.setTimeRangeStart(request.getTimeRangeStart());
        declaration.setTimeRangeEnd(request.getTimeRangeEnd());
        declaration.setUpdateFrequency(request.getUpdateFrequency());
        declaration.setIntendedUse(request.getIntendedUse());
        declaration.setAdditionalNotes(request.getAdditionalNotes());
        declaration.setMethodologyExplanation(request.getMethodologyExplanation());
        declaration.setLicenseStatement(request.getLicenseStatement());
        declaration.setProofDocumentUrl(request.getProofDocument());

        return providerDeclarationRepository.save(declaration);

    }

    @Override
    public ProviderDeclaration findByVersionId(Long versionId) {
        return providerDeclarationRepository.findByDatasetVersionId(versionId)
                .orElseThrow(() -> new CustomException(ErrorCode.DATASET_062));
    }

    @Override
    public ProviderDeclaration getByVersionId(Long versionId) {
        return providerDeclarationRepository.findByDatasetVersionId(versionId)
                .orElse(null);    }

    @Override
    public ProviderDeclaration findByDatasetVersionId(Long versionId) {
        return providerDeclarationRepository.findByDatasetVersionId(versionId)
                .orElseThrow(() -> new CustomException(ErrorCode.DATASET_062));
    }
}

