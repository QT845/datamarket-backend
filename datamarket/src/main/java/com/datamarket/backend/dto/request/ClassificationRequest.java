package com.datamarket.backend.dto.request;

import com.datamarket.backend.enums.DatasetLevel;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClassificationRequest {
    @NotNull
    private DatasetLevel datasetLevel;
    private Long derivedFromVersionId;
}
