package com.datamarket.backend.service.auth;

import com.datamarket.backend.dto.request.LoginRequest;
import com.datamarket.backend.dto.request.RegisterRequest;
import com.datamarket.backend.dto.response.ApiResponse;
import com.datamarket.backend.dto.response.AuthResponse;
import com.datamarket.backend.entity.User;
import com.datamarket.backend.enums.RoleType;
import com.datamarket.backend.enums.UserStatus;
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

    @Override
    public ApiResponse<AuthResponse> login(LoginRequest request) {
        String loginInput = request.getLogin();
        boolean emailLogin = loginInput.contains("@");
        Optional<User> user = emailLogin ?
                userService.getUserByEmail(loginInput):
                userService.getUserByUsername(loginInput);

        if(user.isEmpty()){
            return ApiResponse.<AuthResponse>builder()
                    .success(false)
                    .message("Invalid username/email or password")
                    .build();
        }

        if(!passwordUtil.matches(request.getPassword(), user.get().getPassword())){
            return ApiResponse.<AuthResponse>builder()
                    .success(false)
                    .message("Invalid username/email or password")
                    .build();
        }

        if (user.get().getStatus() == UserStatus.BANNED) {
            return ApiResponse.<AuthResponse>builder()
                    .success(false)
                    .message("Your account has been banned")
                    .build();
        }

        if (user.get().getStatus() == UserStatus.INACTIVE) {
            return ApiResponse.<AuthResponse>builder()
                    .success(false)
                    .message("Please verify your email before logging in")
                    .build();
        }

        AuthResponse authResponse = AuthResponse.builder()
                .userName(user.get().getUsername())
                .email(user.get().getEmail())
                .role(user.get().getRole().name())
                .accessToken(null)
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
            return ApiResponse.<AuthResponse>builder()
                    .success(false)
                    .message("Username is already taken")
                    .build();
        }
        if(userService.existsByEmail(request.getEmail())){
            return ApiResponse.<AuthResponse>builder()
                    .success(false)
                    .message("Email is already in use")
                    .build();
        }

        if(!request.getPassword().equals(request.getConfirmPassword())) {
            return ApiResponse.<AuthResponse>builder()
                    .success(false)
                    .message("Passwords do not match")
                    .build();
        }

        User user = User.builder()
                .username(request.getUserName())
                .email(request.getEmail())
                .password(passwordUtil.hash(request.getPassword()))
                .role(RoleType.CONSUMER)
                .status(UserStatus.INACTIVE)
                .isEmailVerified(false)
                .build();

        userService.saveUser(user);

        AuthResponse response = AuthResponse.builder()
                .userName(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .accessToken(null)
                .build();

        return ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Registration successful")
                .data(response)
                .build();
    }
}
