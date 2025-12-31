package com.datamarket.backend.service.dataset;

import com.datamarket.backend.dto.response.DatasetQualityReportResponse;
import com.datamarket.backend.entity.dataset.DatasetQualityReport;
import com.datamarket.backend.entity.dataset.DatasetVersion;
import com.datamarket.backend.enums.DatasetVersionStatus;
import com.datamarket.backend.enums.ValidationStatus;
import com.datamarket.backend.exception.CustomException;
import com.datamarket.backend.exception.ErrorCode;
import com.datamarket.backend.repository.dataset.DatasetQualityReportRepository;
import com.datamarket.backend.service.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DatasetValidationServiceImpl implements DatasetValidationService{
    private final DatasetQualityReportRepository datasetQualityReportRepository;
    private final DatasetVersionService datasetVersionService;
    private final StorageService storageService;

    @Override
    public DatasetQualityReportResponse validateDatasetVersion(Long versionId) {
        boolean passed = true;
        StringBuilder notes = new StringBuilder();


        DatasetVersion datasetVersion = datasetVersionService.findById(versionId);

        if (!datasetVersion.getStatus().equals(DatasetVersionStatus.CREATED)) {
            throw new CustomException(ErrorCode.DATASET_026);
        }

        byte[] file;
        try {
            file = storageService.read(datasetVersion.getDataLocation());
        } catch (Exception e) {
            throw new CustomException(ErrorCode.COMMON_016);
        }

        String content = new String(file, StandardCharsets.UTF_8);
        String[] lines = content.split("\\R");
        String headerLine = lines[0];
        String[] headers = headerLine.split(",");
        int columnCount = headers.length;
        int recordCount = lines.length - 1;
        int nullCellCount = 0;
        int totalCellCount = columnCount * recordCount;

        if(lines.length < 2) {
            passed = false;
            notes.append("Dataset contains header only (no data rows) \n");
        }

        for(int i = 1; i < lines.length; i++) {
            String[] cells = lines[i].split(",");
            for(String cell : cells) {
                if(cell == null || cell.trim().isEmpty()) {
                    nullCellCount++;
                }
            }
        }

        Set<String> uniqueRows = new HashSet<>();
        for (int i = 1; i < lines.length; i++) {
            uniqueRows.add(lines[i]);
        }

        int duplicateCount = recordCount - uniqueRows.size();
        double duplicateRate = 0.0;
        double nullRate = 0.0;

        if(recordCount < 1) {
            passed = false;
            notes.append("Dataset has no data rows\n");
        } else {
            duplicateRate = (double) duplicateCount / recordCount;
            if(duplicateRate > 0.3) {
                passed = false;
                notes.append("Dataset is not valid: duplicate rate exceeds 30% \n");
            }
            nullRate = (double) nullCellCount / totalCellCount;
            if(nullRate > 0.5) {
                passed = false;
                notes.append("Dataset is not valid: null rate exceeds 50% \n");
            }
        }

        if(columnCount < 1) {
            passed = false;
            notes.append("Dataset has no columns \n");
        }

        ValidationStatus status = ValidationStatus.PASSED;
        if(passed) {
            datasetVersion.setStatus(DatasetVersionStatus.PENDING_REVIEW);

        } else {
            status = ValidationStatus.FAILED;
            datasetVersion.setStatus(DatasetVersionStatus.REJECTED_SYS);
        }
        datasetVersionService.save(datasetVersion);

        boolean formatValid = columnCount > 0 && recordCount >= 1;
        DatasetQualityReport report = DatasetQualityReport.builder()
                .datasetVersion(datasetVersion)
                .recordCount((long) recordCount)
                .nullRate(nullRate)
                .duplicateRate(duplicateRate)
                .formatValid(formatValid)
                .notes(notes.toString())
                .status(status)
                .build();
        datasetQualityReportRepository.save(report);

        return DatasetQualityReportResponse.builder()
                .id(report.getId().toString())
                .datasetId(datasetVersion.getDataset().getId())
                .datasetName(datasetVersion.getDataset().getName())
                .versionId(datasetVersion.getId())
                .version(datasetVersion.getVersion())
                .recordCount(report.getRecordCount())
                .nullRate(report.getNullRate())
                .duplicateRate(report.getDuplicateRate())
                .formatValid(report.isFormatValid())
                .notes(report.getNotes())
                .reportCreatedAt(report.getCreatedAt())
                .versionCreatedAt(datasetVersion.getCreatedAt())
                .build();
    }

    @Override
    public List<DatasetQualityReportResponse> getAll() {
        List<DatasetQualityReport> reports = datasetQualityReportRepository.findByDatasetVersion_Status(DatasetVersionStatus.PENDING_REVIEW);

        return reports.stream()
                .map(report -> DatasetQualityReportResponse.builder()
                        .id(report.getId().toString())
                        .datasetId(report.getDatasetVersion().getDataset().getId())
                        .datasetName(report.getDatasetVersion().getDataset().getName())
                        .versionId(report.getDatasetVersion().getId())
                        .version(report.getDatasetVersion().getVersion())
                        .recordCount(report.getRecordCount())
                        .nullRate(report.getNullRate())
                        .duplicateRate(report.getDuplicateRate())
                        .formatValid(report.isFormatValid())
                        .notes(report.getNotes())
                        .reportCreatedAt(report.getCreatedAt())
                        .versionCreatedAt(report.getDatasetVersion().getCreatedAt())
                        .build())
                .toList();
    }

    @Override
    public DatasetQualityReportResponse getById(Long id) {
        DatasetQualityReport report = datasetQualityReportRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.DATASET_025));
        return DatasetQualityReportResponse.builder()
                .id(report.getId().toString())
                .datasetId(report.getDatasetVersion().getDataset().getId())
                .datasetName(report.getDatasetVersion().getDataset().getName())
                .versionId(report.getDatasetVersion().getId())
                .version(report.getDatasetVersion().getVersion())
                .recordCount(report.getRecordCount())
                .nullRate(report.getNullRate())
                .duplicateRate(report.getDuplicateRate())
                .formatValid(report.isFormatValid())
                .notes(report.getNotes())
                .reportCreatedAt(report.getCreatedAt())
                .versionCreatedAt(report.getDatasetVersion().getCreatedAt())
                .build();
    }
}
