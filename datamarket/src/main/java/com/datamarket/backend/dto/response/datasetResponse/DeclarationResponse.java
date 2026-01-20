package com.datamarket.backend.dto.response.datasetResponse;

import com.datamarket.backend.enums.IntendedUse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeclarationResponse {
    private Long id;
    private String version;
    private String dataSource;
    private String collectionMethod;
    private LocalDateTime timeRangeStart;
    private LocalDateTime timeRangeEnd;
    private String updateFrequency;
    private IntendedUse intendedUse;
    private String additionalNotes;
    private LocalDateTime createdAt;
}
