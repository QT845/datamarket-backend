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
    public ModReviewResponse approveDataset(Long versionId) {
        DatasetVersion version = datasetVersionService.findById(versionId);
        Dataset dataset = version.getDataset();

        if(!version.getStatus().equals(DatasetVersionStatus.TECHNICAL_REVIEW)){
            throw new CustomException(ErrorCode.DATASET_023);
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
                .action("APPROVE")
                .status("APPROVED")
                .message("Dataset approved")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public ModReviewResponse rejectDataset(Long versionId, String reason) {
        DatasetVersion version = datasetVersionService.findById(versionId);
        Dataset dataset = version.getDataset();

        if(!version.getStatus().equals(DatasetVersionStatus.TECHNICAL_REVIEW)){
            throw new CustomException(ErrorCode.DATASET_023);
        }

        version.setStatus(DatasetVersionStatus.REJECTED_MOD);
        datasetVersionService.save(version);

        return ModReviewResponse.builder()
                .datasetId(dataset.getId())
                .datasetName(dataset.getName())
                .versionId(version.getId())
                .version(version.getVersion())
                .action("REJECTED")
                .status("REJECTED")
                .message("Dataset rejected: " + reason)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
