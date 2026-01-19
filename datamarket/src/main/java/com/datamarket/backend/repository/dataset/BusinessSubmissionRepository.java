package com.datamarket.backend.repository.dataset;

import com.datamarket.backend.entity.dataset.BusinessSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessSubmissionRepository extends JpaRepository<BusinessSubmission, Long>
{
}
