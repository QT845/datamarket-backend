package com.datamarket.backend.service.dataset;

import com.datamarket.backend.dto.request.CreateDomainRequest;
import com.datamarket.backend.entity.dataset.DatasetDomain;

import java.util.List;

public interface DatasetDomainService {
    DatasetDomain findById(Long id);
    List<DatasetDomain> getAllDomains();
    DatasetDomain createDomain(CreateDomainRequest request);
}
