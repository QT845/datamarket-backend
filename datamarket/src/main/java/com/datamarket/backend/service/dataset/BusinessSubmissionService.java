package com.datamarket.backend.service.dataset;

import com.datamarket.backend.entity.dataset.BusinessSubmission;

public interface BusinessSubmissionService {
    BusinessSubmission submit(Long versionId);
}
