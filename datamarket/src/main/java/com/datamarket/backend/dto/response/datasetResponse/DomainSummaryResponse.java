package com.datamarket.backend.dto.response.datasetResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DomainSummaryResponse {
    private Long id;
    private String name;
}
