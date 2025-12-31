package com.datamarket.backend.service.dataset;

import com.datamarket.backend.dto.request.CreateDomainRequest;
import com.datamarket.backend.dto.response.DomainResponse;
import com.datamarket.backend.dto.response.DomainSummaryResponse;
import com.datamarket.backend.entity.dataset.DatasetDomain;
import com.datamarket.backend.exception.CustomException;
import com.datamarket.backend.exception.ErrorCode;
import com.datamarket.backend.repository.dataset.DatasetDomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DatasetDomainServiceImpl implements  DatasetDomainService {
    private final DatasetDomainRepository datasetDomainRepository;

    @Override
    public DatasetDomain findById(Long id) {
        return datasetDomainRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.DATASET_010));
    }

    @Override
    public List<DomainSummaryResponse> getAllDomains() {
        List<DatasetDomain> datasetDomain = datasetDomainRepository.findAll();
        return datasetDomain.stream()
                .map(domain -> DomainSummaryResponse.builder()
                        .id(domain.getId())
                        .name(domain.getName())
                        .build())
                .toList();
    }

    @Override
    public DomainResponse createDomain(CreateDomainRequest request) {

        DatasetDomain datasetDomain = new DatasetDomain();
        datasetDomain.setCode(request.getCode());
        datasetDomain.setName(request.getName());
        datasetDomain.setDescription(request.getDescription());
        datasetDomainRepository.save(datasetDomain);

        return DomainResponse.builder()
                .id(datasetDomain.getId())
                .code(datasetDomain.getCode())
                .name(datasetDomain.getName())
                .description(datasetDomain.getDescription())
                .build();
    }
}
