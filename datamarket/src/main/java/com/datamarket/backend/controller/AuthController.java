package com.datamarket.backend.controller;

import com.datamarket.backend.dto.request.LoginRequest;
import com.datamarket.backend.dto.request.RegisterRequest;
import com.datamarket.backend.dto.response.ApiResponse;
import com.datamarket.backend.dto.response.AuthResponse;
import com.datamarket.backend.dto.response.TokenResponse;
import com.datamarket.backend.dto.response.UserResponse;
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

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        ApiResponse<AuthResponse> result = authService.login(loginRequest);

        AuthResponse authResponse = result.getData();

        String refreshToken = authResponse.getRefreshToken();

        setRefreshCookie(response, refreshToken);

        authResponse.setRefreshToken(null);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok().body(authService.register(registerRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = extractRefreshToken(request);
        if (refreshToken != null) {
            ApiResponse<TokenResponse> result =  authService.refreshToken(refreshToken);

            TokenResponse tokenResponse = result.getData();

            String newRefreshToken = tokenResponse.getRefreshToken();

            setRefreshCookie(response, newRefreshToken);

            tokenResponse.setRefreshToken(null);

            return ResponseEntity.ok().body(result);
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
        return ResponseEntity.ok().body(authService.getMe());
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
