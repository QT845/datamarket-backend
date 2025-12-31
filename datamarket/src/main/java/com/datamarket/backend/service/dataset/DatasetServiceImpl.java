package com.datamarket.backend.service.dataset;

import com.datamarket.backend.dto.request.CreateDatasetRequest;
import com.datamarket.backend.dto.request.UpdateDatasetRequest;
import com.datamarket.backend.dto.response.DatasetResponse;
import com.datamarket.backend.entity.dataset.Dataset;
import com.datamarket.backend.enums.DatasetStatus;
import com.datamarket.backend.enums.DatasetType;
import com.datamarket.backend.exception.CustomException;
import com.datamarket.backend.exception.ErrorCode;
import com.datamarket.backend.repository.dataset.DatasetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DatasetServiceImpl implements DatasetService {
    private final DatasetRepository datasetRepository;
    private final DatasetDomainService datasetDomainService;

    @Override
    public DatasetResponse createDataset(CreateDatasetRequest request) {
        Dataset dataset = Dataset.builder()
                .name(request.getName())
                .description(request.getDescription())
//                .domain(datasetDomainService.findById(request.getDomainId()))
                .status(DatasetStatus.DRAFT)
                .currentVersion(null)
                .build();
        datasetRepository.save(dataset);

        return DatasetResponse.builder()
                .id(dataset.getId())
                .name(dataset.getName())
                .description(dataset.getDescription())
                .datasetStatus(dataset.getStatus())
//                .domain(dataset.getDomain().getName())
                .currentVersion(null)
                .createdAt(dataset.getCreatedAt())
                .build();
    }

    @Override
    public DatasetResponse updateDataset(Long datasetId, UpdateDatasetRequest request) {
        Dataset dataset = datasetRepository.findById(datasetId).orElseThrow(() -> new CustomException(ErrorCode.DATASET_001));

        dataset.setName(request.getName());
        dataset.setDescription(request.getDescription());
        datasetRepository.save(dataset);

        return DatasetResponse.builder()
                .id(dataset.getId())
                .name(dataset.getName())
                .description(dataset.getDescription())
                .datasetStatus(dataset.getStatus())
//                .domain(dataset.getDomain().getName())
                .currentVersion(dataset.getCurrentVersion() != null ? dataset.getCurrentVersion().getVersion() : null)
                .createdAt(dataset.getCreatedAt())
                .build();
    }

    @Override
    public DatasetResponse getDataset(Long datasetId) {
        Dataset dataset = datasetRepository.findById(datasetId)
                .orElseThrow(() -> new CustomException(ErrorCode.DATASET_001));

        return DatasetResponse.builder()
                .id(dataset.getId())
                .name(dataset.getName())
                .description(dataset.getDescription())
                .datasetStatus(dataset.getStatus())
//                .domain(dataset.getDomain().getName())
                .currentVersion(dataset.getCurrentVersion() != null ? dataset.getCurrentVersion().getVersion() : null)
                .createdAt(dataset.getCreatedAt())
                .build();
    }

    @Override
    public void archiveDataset(Long datasetId) {
        Dataset dataset = datasetRepository.findById(datasetId)
                .orElseThrow(() -> new CustomException(ErrorCode.DATASET_001));
        dataset.setStatus(DatasetStatus.DELETED);
        datasetRepository.save(dataset);
    }

    @Override
    public void switchCurrentVersion(Long datasetId, Long versionId) {

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
