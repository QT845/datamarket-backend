package com.datamarket.backend.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokenResponse {
    String accessToken;
    String refreshToken;
}
