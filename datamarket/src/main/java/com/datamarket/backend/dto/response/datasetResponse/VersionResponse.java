package com.datamarket.backend.dto.response.datasetResponse;

import com.datamarket.backend.enums.DatasetGenerationType;
import com.datamarket.backend.enums.DatasetVersionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VersionResponse {
    private Long id;
    private Long datasetId;
    private String version;
    private DatasetVersionStatus status;
    private DatasetGenerationType generationType;
    private String dataLocation;
    private String checksum;
    private String originFileName;
    private LocalDateTime createdAt;
}
