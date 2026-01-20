package com.datamarket.backend.mapper;

import com.datamarket.backend.dto.response.datasetResponse.DatasetResponse;
import com.datamarket.backend.entity.dataset.Dataset;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DatasetMapper {
    @Mapping(target = "domain",
            source = "domain.name")
    @Mapping(target = "currentVersion",
            source = "currentVersion.version")
    @Mapping(target = "datasetStatus",
            source = "status")
    DatasetResponse toDatasetResponse(Dataset dataset);
}
