package com.datamarket.backend.service.auth;

import com.datamarket.backend.dto.request.LoginRequest;
import com.datamarket.backend.dto.request.RegisterRequest;
import com.datamarket.backend.dto.response.authResponse.AuthResponse;
import com.datamarket.backend.dto.response.authResponse.TokenResponse;
import com.datamarket.backend.entity.User;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse register(RegisterRequest request);
    TokenResponse refreshToken(String refreshToken);
    void logout(String refreshToken);
    User getMe();
}
