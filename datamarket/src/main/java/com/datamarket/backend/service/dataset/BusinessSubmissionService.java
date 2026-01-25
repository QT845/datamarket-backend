package com.datamarket.backend.service.dataset;

import com.datamarket.backend.entity.dataset.BusinessSubmission;

import java.util.List;

public interface BusinessSubmissionService {
    BusinessSubmission submit(Long versionId);
    List<BusinessSubmission> getAll();
    BusinessSubmission getById(Long id);
}
