package com.datamarket.backend.dto.response.datasetResponse;

import com.datamarket.backend.enums.DatasetLevel;
import com.datamarket.backend.enums.IntendedUse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmissionReportResponse {
    private Long id;
    private Long datasetId;
    private String datasetName;
    private Long versionId;
    private String versionName;
    private String dataSource;
    private String collectionMethod;
    private LocalDateTime timeRangeStart;
    private LocalDateTime timeRangeEnd;
    private String updateFrequency;
    private IntendedUse intendedUse;
    private String additionalNotes;
    private String derivedFromVersion;
    private DatasetLevel datasetLevel;
    private LocalDateTime reportCreatedAt;
    private String submittedBy;

}
