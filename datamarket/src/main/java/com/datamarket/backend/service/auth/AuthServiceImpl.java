package com.datamarket.backend.service.auth;

import com.datamarket.backend.dto.request.LoginRequest;
import com.datamarket.backend.dto.request.RegisterRequest;
import com.datamarket.backend.dto.response.ApiResponse;
import com.datamarket.backend.dto.response.AuthResponse;
import com.datamarket.backend.dto.response.TokenResponse;
import com.datamarket.backend.dto.response.UserResponse;
import com.datamarket.backend.entity.RefreshToken;
import com.datamarket.backend.entity.User;
import com.datamarket.backend.enums.RoleType;
import com.datamarket.backend.enums.UserStatus;
import com.datamarket.backend.exception.CustomException;
import com.datamarket.backend.exception.ErrorCode;
import com.datamarket.backend.mapper.UserMapper;
import com.datamarket.backend.security.jwt.JwtTokenProvider;
import com.datamarket.backend.security.refresh.RefreshTokenService;
import com.datamarket.backend.security.util.SecurityUtil;
import com.datamarket.backend.service.user.UserService;
import com.datamarket.backend.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserService userService;
    private final PasswordUtil passwordUtil;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userService.getUserByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.AUTH_001));

        if(!passwordUtil.matches(request.getPassword(), user.getPassword())){
            throw new CustomException(ErrorCode.AUTH_001);
        }

        if (user.getStatus() == UserStatus.BANNED) {
            throw new CustomException(ErrorCode.AUTH_008);
        }

        if (user.getStatus() == UserStatus.INACTIVE) {
            throw new CustomException(ErrorCode.AUTH_009);
        }
        String accessToken = jwtTokenProvider.generateAccessToken(user.getId(),
                user.getRole().name(),
                user.getTokenVersion());

        RefreshToken refreshTokenEntity = refreshTokenService.createRefreshToken(user);
        String refreshToken = refreshTokenEntity.getRefreshToken();

        return AuthResponse.builder()
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if(userService.existsByEmail(request.getEmail())){
            throw new CustomException(ErrorCode.USER_002);
        }

        if(!request.getPassword().equals(request.getConfirmPassword())) {
            throw new CustomException(ErrorCode.USER_008);
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordUtil.hash(request.getPassword()))
                .role(RoleType.CONSUMER)
                .status(UserStatus.INACTIVE)
                .isEmailVerified(false)
                .tokenVersion(0)
                .build();

        userService.saveUser(user);

        return AuthResponse.builder()
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .accessToken(null)
                .refreshToken(null)
                .build();
    }

    @Override
    public TokenResponse refreshToken(String refreshToken) {
        return refreshTokenService.refreshAccessToken(refreshToken);
    }

    @Override
    public void logout(String refreshToken) {
        refreshTokenService.deleteByToken(refreshToken);

        ApiResponse.<String>builder()
                .success(true)
                .message("Logged out successfully")
                .data(null)
                .build();
    }

    @Override
    public User getMe() {
        return SecurityUtil.getCurrentUser();
    }
}
