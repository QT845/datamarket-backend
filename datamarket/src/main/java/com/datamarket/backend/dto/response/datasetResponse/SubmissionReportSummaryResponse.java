package com.datamarket.backend.dto.response.datasetResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmissionReportSummaryResponse {
    private Long id;
    private String datasetName;
    private String versionName;
    private LocalDateTime reportCreatedAt;

}
