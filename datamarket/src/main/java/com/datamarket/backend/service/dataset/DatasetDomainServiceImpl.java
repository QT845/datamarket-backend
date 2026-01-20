package com.datamarket.backend.service.dataset;

import com.datamarket.backend.dto.request.CreateDomainRequest;
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
    public List<DatasetDomain> getAllDomains() {
        return datasetDomainRepository.findAll();
    }

    @Override
    public DatasetDomain createDomain(CreateDomainRequest request) {

        DatasetDomain datasetDomain = new DatasetDomain();
        datasetDomain.setCode(request.getCode());
        datasetDomain.setName(request.getName());
        datasetDomain.setDescription(request.getDescription());

        return datasetDomainRepository.save(datasetDomain);
    }
}
