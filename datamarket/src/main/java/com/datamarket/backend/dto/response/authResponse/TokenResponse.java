package com.datamarket.backend.dto.response.authResponse;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokenResponse {
    String accessToken;
    String refreshToken;
}
