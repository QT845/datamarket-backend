package com.datamarket.backend.mapper;

import com.datamarket.backend.dto.response.datasetResponse.DatasetQualityReportResponse;
import com.datamarket.backend.dto.response.datasetResponse.DatasetQualityReportSummaryResponse;
import com.datamarket.backend.entity.dataset.DatasetQualityReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DatasetQualityReportMapper {

    @Mapping(target = "versionId",
            source = "datasetVersion.id")
    @Mapping(target = "version",
            source = "datasetVersion.version")
    @Mapping(target = "datasetId",
            source = "datasetVersion.dataset.id")
    @Mapping(target = "datasetName",
            source = "datasetVersion.dataset.name")
    @Mapping(target = "versionCreatedAt",
            source = "datasetVersion.createdAt")
    @Mapping(target = "reportCreatedAt",
            source = "createdAt")
    DatasetQualityReportResponse toQualityReport(DatasetQualityReport datasetQualityReport);

    @Mapping(target = "version",
            source = "datasetVersion.version")
    @Mapping(target = "datasetName",
            source = "datasetVersion.dataset.name")
    @Mapping(target = "reportCreatedAt",
            source = "createdAt")
    List<DatasetQualityReportSummaryResponse> toQualityReports(List<DatasetQualityReport> datasetQualityReports);


}
