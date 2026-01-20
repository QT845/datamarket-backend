package com.datamarket.backend.mapper;

import com.datamarket.backend.dto.response.datasetResponse.VersionResponse;
import com.datamarket.backend.entity.dataset.DatasetVersion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DatasetVersionMapper {
    @Mapping(target = "datasetId",
            source = "dataset.id")
    VersionResponse toVersionResponse(DatasetVersion datasetVersion);
}
