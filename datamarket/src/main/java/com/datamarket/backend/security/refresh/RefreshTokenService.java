package com.datamarket.backend.security.refresh;

import com.datamarket.backend.dto.response.TokenResponse;
import com.datamarket.backend.entity.RefreshToken;
import com.datamarket.backend.entity.User;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(User user);
    RefreshToken validateRefreshToken(String token);
    TokenResponse refreshAccessToken(String refreshToken);
    RefreshToken rotateRefreshToken(RefreshToken oldToken);
    void revokeAllTokensByUser(User user);
    void revokeToken(String token);
    void cleanUpExpired();
}
