package com.datamarket.backend.security.refresh;

import com.datamarket.backend.dto.response.TokenResponse;
import com.datamarket.backend.entity.RefreshToken;
import com.datamarket.backend.entity.User;
import com.datamarket.backend.enums.UserStatus;
import com.datamarket.backend.repository.RefreshTokenRepository;
import com.datamarket.backend.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final TokenProvider refreshTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${refresh-token.expire-days}")
    private int refreshTokenExpireDays;

    @Override
    public RefreshToken createRefreshToken(User user) {
        String token = refreshTokenProvider.generateRefreshToken();
        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(token)
                .user(user)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusDays(refreshTokenExpireDays))
                .revoked(false)
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken validateRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        if (refreshToken.isRevoked() || refreshToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token is expired or revoked");
        }
        if (refreshToken.getUser().getStatus() == UserStatus.BANNED) {
            throw new RuntimeException("User is banned");
        }
        return refreshToken;
    }

    @Override
    public TokenResponse refreshAccessToken(String refreshToken) {
        RefreshToken token = validateRefreshToken(refreshToken);
        User user = token.getUser();
        if (user.getStatus() == UserStatus.BANNED) {
            throw new RuntimeException("User is banned");
        }
        RefreshToken newRefreshToken = rotateRefreshToken(token);
        String newAccessToken = jwtTokenProvider.generateAccessToken(
                user.getId(),
                user.getRole().toString(),
                user.getTokenVersion()
        );

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken.getRefreshToken())
                .build();
    }

    @Override
    public RefreshToken rotateRefreshToken(RefreshToken oldToken) {
        revokeToken(oldToken.getRefreshToken());
        return createRefreshToken(oldToken.getUser());
    }

    @Override
    public void revokeAllTokensByUser(User user) {
        List<RefreshToken> tokens = refreshTokenRepository.findAllByUserAndRevokedFalse(user);
        for (RefreshToken token : tokens) {
            token.setRevoked(true);
        }
        refreshTokenRepository.saveAll(tokens);
    }

    @Override
    public void revokeToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void cleanUpExpired() {
        refreshTokenRepository.deleteByExpiredAtBefore(LocalDateTime.now());
    }
}
