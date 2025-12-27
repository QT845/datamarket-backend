package com.datamarket.backend.dto.request;

import com.datamarket.backend.enums.DatasetType;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDatasetRequest {
    @Size(min = 1, max = 100)
    private String name;
    @Size(min = 1, max = 500)
    private String description;
}
