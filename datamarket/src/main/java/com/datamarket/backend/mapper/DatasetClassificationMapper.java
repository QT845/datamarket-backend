package com.datamarket.backend.mapper;

import com.datamarket.backend.dto.response.datasetResponse.ClassificationResponse;
import com.datamarket.backend.entity.dataset.DatasetClassification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DatasetClassificationMapper {

    @Mapping(target = "versionName",
            source = "datasetVersion.version")
    @Mapping(target = "derivedFromVersion",
            source = "derivedFromVersion.version")
    ClassificationResponse toClassification(DatasetClassification datasetClassification);
}
