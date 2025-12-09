package com.datamarket.backend.service.auth;

import com.datamarket.backend.dto.request.LoginRequest;
import com.datamarket.backend.dto.request.RegisterRequest;
import com.datamarket.backend.dto.response.ApiResponse;
import com.datamarket.backend.dto.response.AuthResponse;
import com.datamarket.backend.dto.response.TokenResponse;
import com.datamarket.backend.entity.RefreshToken;
import com.datamarket.backend.entity.User;
import com.datamarket.backend.enums.RoleType;
import com.datamarket.backend.enums.UserStatus;
import com.datamarket.backend.exception.CustomException;
import com.datamarket.backend.exception.ErrorCode;
import com.datamarket.backend.security.jwt.JwtTokenProvider;
import com.datamarket.backend.security.refresh.RefreshTokenService;
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
    public ApiResponse<AuthResponse> login(LoginRequest request) {
        String loginInput = request.getLogin();
        boolean emailLogin = loginInput.contains("@");
        Optional<User> user = emailLogin ?
                userService.getUserByEmail(loginInput):
                userService.getUserByUsername(loginInput);

        if(user.isEmpty()){
            throw new CustomException(ErrorCode.AUTH_001);
        }

        if(!passwordUtil.matches(request.getPassword(), user.get().getPassword())){
            throw new CustomException(ErrorCode.AUTH_001);
        }

        if (user.get().getStatus() == UserStatus.BANNED) {
            throw new CustomException(ErrorCode.AUTH_008);
        }

        if (user.get().getStatus() == UserStatus.INACTIVE) {
            throw new CustomException(ErrorCode.AUTH_009);
        }
        String accessToken = jwtTokenProvider.generateAccessToken(user.get().getId(),
                user.get().getRole().name(),
                user.get().getTokenVersion());

        RefreshToken refreshTokenEntity = refreshTokenService.createRefreshToken(user.get());
        String refreshToken = refreshTokenEntity.getRefreshToken();

        AuthResponse authResponse = AuthResponse.builder()
                .userName(user.get().getUsername())
                .email(user.get().getEmail())
                .role(user.get().getRole().name())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Login successful")
                .data(authResponse)
                .build();
    }

    @Override
    public ApiResponse<AuthResponse> register(RegisterRequest request) {
        if(userService.existsByUsername(request.getUserName())){
            throw new CustomException(ErrorCode.USER_003);
        }
        if(userService.existsByEmail(request.getEmail())){
            throw new CustomException(ErrorCode.USER_002);
        }

        if(!request.getPassword().equals(request.getConfirmPassword())) {
            throw new CustomException(ErrorCode.USER_009);
        }

        User user = User.builder()
                .username(request.getUserName())
                .email(request.getEmail())
                .password(passwordUtil.hash(request.getPassword()))
                .role(RoleType.CONSUMER)
                .status(UserStatus.INACTIVE)
                .isEmailVerified(false)
                .tokenVersion(0)
                .build();

        userService.saveUser(user);

        AuthResponse response = AuthResponse.builder()
                .userName(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .accessToken(null)
                .refreshToken(null)
                .build();

        return ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Registration successful")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<TokenResponse> refreshToken(String refreshToken) {
        TokenResponse tokenResponse = refreshTokenService.refreshAccessToken(refreshToken);

        return ApiResponse.<TokenResponse>builder()
                .success(true)
                .message("Token refreshed successfully")
                .data(tokenResponse)
                .build();
    }

    @Override
    public ApiResponse<String> logout(String refreshToken) {
        refreshTokenService.deleteByToken(refreshToken);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Logged out successfully")
                .data(null)
                .build();
    }
}
