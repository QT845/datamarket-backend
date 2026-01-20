package com.datamarket.backend.dto.response.datasetResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DomainResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
}
