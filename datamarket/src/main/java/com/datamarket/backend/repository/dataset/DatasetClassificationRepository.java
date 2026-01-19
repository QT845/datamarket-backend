package com.datamarket.backend.repository.dataset;

import com.datamarket.backend.entity.dataset.DatasetClassification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DatasetClassificationRepository extends JpaRepository<DatasetClassification,Long> {
    Optional<DatasetClassification> findByDatasetVersionId(Long versionId);
}
