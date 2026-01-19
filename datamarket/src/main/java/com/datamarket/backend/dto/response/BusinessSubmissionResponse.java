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
public class BusinessSubmissionResponse {
    private Long id;
    private String versionName;
    private LocalDateTime submittedAt;
    private String submittedBy;
}
