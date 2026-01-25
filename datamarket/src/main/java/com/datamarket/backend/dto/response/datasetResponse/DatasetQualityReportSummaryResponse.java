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
public class DatasetQualityReportSummaryResponse {
    private Long id;
    private String datasetName;
    private String version;
    private LocalDateTime reportCreatedAt;
}
