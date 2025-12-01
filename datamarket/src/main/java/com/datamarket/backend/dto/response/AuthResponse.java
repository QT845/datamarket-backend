package com.datamarket.backend.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String accessToken;
    private String userName;
    private String email;
    private String role;
}
