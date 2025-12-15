package com.datamarket.backend.service.auth;

import com.datamarket.backend.dto.request.LoginRequest;
import com.datamarket.backend.dto.request.RegisterRequest;
import com.datamarket.backend.dto.response.ApiResponse;
import com.datamarket.backend.dto.response.AuthResponse;
import com.datamarket.backend.dto.response.TokenResponse;
import com.datamarket.backend.dto.response.UserResponse;

public interface AuthService {
    ApiResponse<AuthResponse> login(LoginRequest request);
    ApiResponse<AuthResponse> register(RegisterRequest request);
    ApiResponse<TokenResponse> refreshToken(String refreshToken);
    void logout(String refreshToken);
    ApiResponse<UserResponse> getMe();
}
