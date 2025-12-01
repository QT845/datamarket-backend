package com.datamarket.backend.service.auth;

import com.datamarket.backend.dto.request.LoginRequest;
import com.datamarket.backend.dto.request.RegisterRequest;
import com.datamarket.backend.dto.response.ApiResponse;
import com.datamarket.backend.dto.response.AuthResponse;

public interface AuthService {
    ApiResponse<AuthResponse> login(LoginRequest request);
    ApiResponse<AuthResponse> register(RegisterRequest request);
}
