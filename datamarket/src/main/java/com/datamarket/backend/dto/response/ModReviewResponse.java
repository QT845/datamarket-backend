package com.datamarket.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModReviewResponse {
    private Long versionId;
    private Long datasetId;
    private String datasetName;
    private String version;
    private String status;
    private String action;
    private String message;
    private LocalDateTime timestamp;
}
