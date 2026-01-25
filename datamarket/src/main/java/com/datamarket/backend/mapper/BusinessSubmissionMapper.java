package com.datamarket.backend.mapper;

import com.datamarket.backend.dto.response.datasetResponse.BusinessSubmissionResponse;
import com.datamarket.backend.dto.response.datasetResponse.SubmissionReportResponse;
import com.datamarket.backend.dto.response.datasetResponse.SubmissionReportSummaryResponse;
import com.datamarket.backend.entity.dataset.BusinessSubmission;
import com.datamarket.backend.entity.dataset.DatasetClassification;
import com.datamarket.backend.entity.dataset.ProviderDeclaration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BusinessSubmissionMapper {
    @Mapping(target = "versionName",
            source = "datasetVersion.version")
    @Mapping(target = "submittedBy",
            source = "submittedBy.user.fullName")
    BusinessSubmissionResponse toResponse(BusinessSubmission submission);


    @Mapping(target = "versionName",
            source = "datasetVersion.version")
    @Mapping(target = "reportCreatedAt",
            source = "submittedAt")
    @Mapping(target = "datasetName",
            source = "datasetVersion.dataset.name")
    List<SubmissionReportSummaryResponse> toSubmissionReports(List<BusinessSubmission> submissions);


    @Mapping(target = "id",
            source = "submission.id")
    @Mapping(target = "versionId",
            source = "submission.datasetVersion.id")
    @Mapping(target = "versionName",
            source = "submission.datasetVersion.version")
    @Mapping(target = "datasetId",
            source = "submission.datasetVersion.dataset.id")
    @Mapping(target = "datasetName",
            source = "submission.datasetVersion.dataset.name")
    @Mapping(target = "submittedBy",
            source = "submission.submittedBy.user.fullName")
    @Mapping(target = "reportCreatedAt",
            source = "submission.submittedAt")
    @Mapping(target = "derivedFromVersion",
            source = "classification.derivedFromVersion.version")
    SubmissionReportResponse toSubmissionReport(BusinessSubmission submission, ProviderDeclaration declaration,
                                                DatasetClassification classification);
}
