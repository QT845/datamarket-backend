package com.datamarket.backend.service.moderator;

import com.datamarket.backend.dto.response.ModReviewResponse;
import com.datamarket.backend.entity.dataset.Dataset;
import com.datamarket.backend.entity.dataset.DatasetVersion;
import com.datamarket.backend.enums.DatasetStatus;
import com.datamarket.backend.enums.DatasetVersionStatus;
import com.datamarket.backend.exception.CustomException;
import com.datamarket.backend.exception.ErrorCode;
import com.datamarket.backend.service.dataset.DatasetService;
import com.datamarket.backend.service.dataset.DatasetValidationService;
import com.datamarket.backend.service.dataset.DatasetVersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ModeratorServiceImpl implements ModeratorService{
    private final DatasetVersionService datasetVersionService;
    private final DatasetService datasetService;

    @Override
    public ModReviewResponse approveTechnical(Long versionId) {
        DatasetVersion version = datasetVersionService.findById(versionId);
        Dataset dataset = version.getDataset();

        if(!version.getStatus().equals(DatasetVersionStatus.TECHNICAL_REVIEW)){
            throw new CustomException(ErrorCode.DATASET_023);
        }

        dataset.setStatus(DatasetStatus.PENDING);
        datasetService.save(dataset);

        version.setStatus(DatasetVersionStatus.TECHNICAL_APPROVED);
        datasetVersionService.save(version);

        return ModReviewResponse.builder()
                .datasetId(dataset.getId())
                .datasetName(dataset.getName())
                .versionId(version.getId())
                .version(version.getVersion())
                .action("TECH_APPROVED")
                .status(version.getStatus().name())
                .message("Technical review approved")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public ModReviewResponse rejectTechnical(Long versionId, String reason) {
        DatasetVersion version = datasetVersionService.findById(versionId);
        Dataset dataset = version.getDataset();

        if(!version.getStatus().equals(DatasetVersionStatus.TECHNICAL_REVIEW)){
            throw new CustomException(ErrorCode.DATASET_023);
        }

        version.setStatus(DatasetVersionStatus.REJECTED_MOD);
        datasetVersionService.save(version);

        dataset.setStatus(DatasetStatus.DRAFT);
        datasetService.save(dataset);

        return ModReviewResponse.builder()
                .datasetId(dataset.getId())
                .datasetName(dataset.getName())
                .versionId(version.getId())
                .version(version.getVersion())
                .action("TECH_REJECTED")
                .status(version.getStatus().name())
                .message("Technical review rejected: " + reason)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public ModReviewResponse approveBusiness(Long versionId) {
        DatasetVersion version = datasetVersionService.findById(versionId);
        Dataset dataset = version.getDataset();

        if(!version.getStatus().equals(DatasetVersionStatus.BUSINESS_REVIEW)){
            throw new CustomException(ErrorCode.DATASET_066);
        }

        dataset.setCurrentVersion(version);
        dataset.setStatus(DatasetStatus.APPROVED);
        datasetService.save(dataset);

        version.setStatus(DatasetVersionStatus.APPROVED);
        datasetVersionService.save(version);

        return ModReviewResponse.builder()
                .datasetId(dataset.getId())
                .datasetName(dataset.getName())
                .versionId(version.getId())
                .version(version.getVersion())
                .action("BUSINESS_APPROVE")
                .status(version.getStatus().name())
                .message("Business review approved")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public ModReviewResponse rejectBusiness(Long versionId, String reason) {
        DatasetVersion version = datasetVersionService.findById(versionId);
        Dataset dataset = version.getDataset();

        if(!version.getStatus().equals(DatasetVersionStatus.BUSINESS_REVIEW)){
            throw new CustomException(ErrorCode.DATASET_066);
        }

        version.setStatus(DatasetVersionStatus.REJECTED_MOD);
        datasetVersionService.save(version);

        dataset.setStatus(DatasetStatus.PENDING);
        datasetService.save(dataset);

        return ModReviewResponse.builder()
                .datasetId(dataset.getId())
                .datasetName(dataset.getName())
                .versionId(version.getId())
                .version(version.getVersion())
                .action("BUSINESS_REJECTED")
                .status(version.getStatus().name())
                .message("Business review rejected: " + reason)
                .timestamp(LocalDateTime.now())
                .build();    }
}
