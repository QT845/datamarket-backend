package com.datamarket.backend.dto.response;

import com.datamarket.backend.enums.DatasetStatus;
import com.datamarket.backend.enums.DatasetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DatasetResponse {
    private Long id;
    private String name;
    private String description;
    private DatasetStatus datasetStatus;
    private String domain;
    private String currentVersion;
    private LocalDateTime createdAt;
}
