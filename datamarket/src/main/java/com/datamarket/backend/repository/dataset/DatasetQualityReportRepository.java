package com.datamarket.backend.repository.dataset;

import com.datamarket.backend.entity.dataset.DatasetQualityReport;
import com.datamarket.backend.enums.DatasetVersionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DatasetQualityReportRepository extends JpaRepository<DatasetQualityReport, Long> {
    Optional<DatasetQualityReport> findByDatasetVersionId(Long versionId);
    List<DatasetQualityReport> findByDatasetVersion_Status(DatasetVersionStatus status);

}
