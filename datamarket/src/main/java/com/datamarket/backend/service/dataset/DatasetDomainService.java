package com.datamarket.backend.service.dataset;

import com.datamarket.backend.dto.request.CreateDomainRequest;
import com.datamarket.backend.dto.response.DomainResponse;
import com.datamarket.backend.dto.response.DomainSummaryResponse;
import com.datamarket.backend.entity.dataset.DatasetDomain;

import java.util.List;

public interface DatasetDomainService {
    DatasetDomain findById(Long id);
    List<DomainSummaryResponse> getAllDomains();
    DomainResponse createDomain(CreateDomainRequest request);
}
