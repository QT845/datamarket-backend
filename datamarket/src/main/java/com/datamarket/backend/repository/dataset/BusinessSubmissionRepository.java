package com.datamarket.backend.repository.dataset;

import com.datamarket.backend.entity.dataset.BusinessSubmission;
import com.datamarket.backend.enums.DatasetVersionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessSubmissionRepository extends JpaRepository<BusinessSubmission, Long> {
    List<BusinessSubmission> findByDatasetVersion_Status(DatasetVersionStatus status);
}
