package com.datamarket.backend.dto.request;

import com.datamarket.backend.enums.IntendedUse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeclarationRequest {
    @NotBlank
    private String dataSource;
    @NotBlank
    private String collectionMethod;
    @NotNull
    private LocalDateTime timeRangeStart;
    @NotNull
    private LocalDateTime timeRangeEnd;
    @NotBlank
    private String updateFrequency;
    @NotNull
    private IntendedUse intendedUse;
    private String additionalNotes;
    private String methodologyExplanation;
    private String licenseStatement;
    private String proofDocument;
}
