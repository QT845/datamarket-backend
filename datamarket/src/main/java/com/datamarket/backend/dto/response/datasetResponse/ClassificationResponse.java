package com.datamarket.backend.dto.response.datasetResponse;

import com.datamarket.backend.enums.DatasetLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassificationResponse {
    private Long id;
    private String versionName;
    private String derivedFromVersion;
    private DatasetLevel datasetLevel;
}
