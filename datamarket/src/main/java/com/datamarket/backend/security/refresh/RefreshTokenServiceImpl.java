package com.datamarket.backend.security.refresh;

import com.datamarket.backend.dto.response.authResponse.TokenResponse;
import com.datamarket.backend.entity.RefreshToken;
import com.datamarket.backend.entity.User;
import com.datamarket.backend.enums.UserStatus;
import com.datamarket.backend.exception.CustomException;
import com.datamarket.backend.exception.ErrorCode;
import com.datamarket.backend.repository.RefreshTokenRepository;
import com.datamarket.backend.security.jwt.JwtTokenProvider;
import com.datamarket.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final TokenProvider refreshTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Value("${refresh-token.expire-days}")
    private int refreshTokenExpireDays;

    @Override
    @Transactional
    public RefreshToken createRefreshToken(User user) {
        refreshTokenRepository.deleteByUser(user);

        String token = refreshTokenProvider.generateRefreshToken();

        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(token)
                .user(user)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusDays(refreshTokenExpireDays))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    @Transactional
    public RefreshToken validateRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token)
                .orElseThrow(() -> new CustomException(ErrorCode.AUTH_007));

        if (refreshToken.getUser().getStatus() == UserStatus.BANNED) {
            throw new CustomException(ErrorCode.AUTH_008);
        }

        if (refreshToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new CustomException(ErrorCode.AUTH_003);
        }

        return refreshToken;
    }

    @Override
    @Transactional
    public TokenResponse refreshAccessToken(String refreshToken) {
        RefreshToken token = validateRefreshToken(refreshToken);
        User user = token.getUser();

        if (user.getStatus() == UserStatus.BANNED) {
            throw new CustomException(ErrorCode.AUTH_008);
        }

        RefreshToken newRefreshToken = createRefreshToken(user);

        user.setTokenVersion(user.getTokenVersion() + 1);

        userService.saveUser(user);

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
    @Transactional
    public void deleteByToken(String refreshToken) {
        RefreshToken token = validateRefreshToken(refreshToken);

        User user = token.getUser();

        user.setTokenVersion(user.getTokenVersion() + 1);

        userService.saveUser(user);

        refreshTokenRepository.deleteByRefreshToken(refreshToken);
    }

    @Override
    public void cleanUpExpired() {
        refreshTokenRepository.deleteByExpiredAtBefore(LocalDateTime.now());
    }


}
