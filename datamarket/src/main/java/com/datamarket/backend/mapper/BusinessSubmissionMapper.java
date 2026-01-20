package com.datamarket.backend.mapper;

import com.datamarket.backend.dto.response.datasetResponse.BusinessSubmissionResponse;
import com.datamarket.backend.entity.dataset.BusinessSubmission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BusinessSubmissionMapper {
    @Mapping(target = "versionName",
            source = "datasetVersion.version")
    @Mapping(target = "submittedBy",
            source = "submittedBy.user.fullName")
    BusinessSubmissionResponse toResponse(BusinessSubmission submission);
}
