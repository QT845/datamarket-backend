package com.datamarket.backend.dto.response;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiErrorResponse {
    private String message;
    private String code;
    private boolean success;
    private int status;
    private String path;
    private LocalDateTime timestamp;
    private List<String> errors;

}
