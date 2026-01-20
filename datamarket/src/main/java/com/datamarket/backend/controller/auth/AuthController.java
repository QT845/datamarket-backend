package com.datamarket.backend.controller.auth;

import com.datamarket.backend.dto.request.LoginRequest;
import com.datamarket.backend.dto.request.RegisterRequest;
import com.datamarket.backend.dto.response.ApiResponse;
import com.datamarket.backend.dto.response.AuthResponse;
import com.datamarket.backend.dto.response.TokenResponse;
import com.datamarket.backend.dto.response.UserResponse;
import com.datamarket.backend.mapper.UserMapper;
import com.datamarket.backend.service.auth.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletResponse response) {
        AuthResponse result = authService.login(loginRequest);

        String refreshToken = result.getRefreshToken();

        setRefreshCookie(response, refreshToken);

        result.setRefreshToken(null);

        ApiResponse<AuthResponse> authResponse = ApiResponse.<AuthResponse>builder()
                .success(true)
                .data(result)
                .message("Login successful")
                .build();

        return ResponseEntity.ok().body(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthResponse response  = authService.register(registerRequest);

        ApiResponse<AuthResponse> apiResponse = ApiResponse.<AuthResponse>builder()
                .success(true)
                .data(response)
                .message("Registration successful")
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = extractRefreshToken(request);
        if (refreshToken != null) {
            TokenResponse result = authService.refreshToken(refreshToken);

            String newRefreshToken = result.getRefreshToken();

            setRefreshCookie(response, newRefreshToken);

            result.setRefreshToken(null);

            ApiResponse<TokenResponse> tokenResponse = ApiResponse.<TokenResponse>builder()
                    .success(true)
                    .message("Token refreshed successfully")
                    .data(result)
                    .build();

            return ResponseEntity.ok().body(tokenResponse);
        } else {
            return ResponseEntity.status(401).body(ApiResponse.<TokenResponse>builder()
                    .success(false)
                    .message("Refresh token is missing")
                    .build());
        }

    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = extractRefreshToken(request);

        if (refreshToken != null) {
            authService.logout(refreshToken);

            ResponseCookie deleteCookie = ResponseCookie.from("refresh_token", "")
                    .httpOnly(true)
                    .secure(false)
                    .path("/api/auth")
                    .maxAge(0)
                    .sameSite("Lax")
                    .build();

            response.addHeader("Set-Cookie", deleteCookie.toString());

            return ResponseEntity.ok().body(ApiResponse.<String>builder()
                    .success(true)
                    .message("Logged out successfully")
                    .build());
        } else {
            return ResponseEntity.status(401).body(ApiResponse.<String>builder()
                    .success(false)
                    .message("Refresh token is missing")
                    .build());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMe() {
        UserResponse userResponse = userMapper.toUserResponse(authService.getMe());
        ApiResponse<UserResponse> apiResponse = ApiResponse.<UserResponse>builder()
                .success(true)
                .data(userResponse)
                .message("User info retrieved successfully")
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    private String extractRefreshToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh_token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void setRefreshCookie(HttpServletResponse response, String token) {
        ResponseCookie cookie = ResponseCookie.from("refresh_token", token)
                .httpOnly(true)
                .secure(false)
                .path("/api/auth")
                .maxAge(3 * 24 * 60 * 60)
                .sameSite("Lax")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

}
