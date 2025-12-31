package com.datamarket.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DatasetQualityReportResponse {
    private String id;
    private Long datasetId;
    private String datasetName;
    private Long versionId;
    private String version;
    private Long recordCount;
    private Double nullRate;
    private Double duplicateRate;
    private boolean formatValid;
    private String notes;
    private LocalDateTime reportCreatedAt;
    private LocalDateTime versionCreatedAt;

}
