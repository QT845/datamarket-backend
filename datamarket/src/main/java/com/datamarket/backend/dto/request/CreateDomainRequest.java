package com.datamarket.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDomainRequest {
    @NotBlank
    @Size(min = 1, max = 50)
    private String code;
    @NotBlank
    @Size(min = 1, max = 100)
    private String name;
    @NotBlank
    @Size(min = 1, max = 500)
    private String description;
}
