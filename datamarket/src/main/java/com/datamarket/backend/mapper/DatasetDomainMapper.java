package com.datamarket.backend.mapper;

import com.datamarket.backend.dto.response.datasetResponse.DomainResponse;
import com.datamarket.backend.dto.response.datasetResponse.DomainSummaryResponse;
import com.datamarket.backend.entity.dataset.DatasetDomain;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DatasetDomainMapper {
    DomainResponse toDomain(DatasetDomain datasetDomain);
    List<DomainSummaryResponse> toDomainSummary(List<DatasetDomain> datasetDomain);
}
