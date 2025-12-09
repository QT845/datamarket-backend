package com.datamarket.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //    CommonError
    COMMON_001("COMMON_001", "Validation failed", HttpStatus.BAD_REQUEST),
    COMMON_002("COMMON_002", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    COMMON_003("COMMON_003", "Resource not found", HttpStatus.NOT_FOUND),
    COMMON_004("COMMON_004", "Bad request", HttpStatus.BAD_REQUEST),
    COMMON_005("COMMON_005", "Method not allowed", HttpStatus.METHOD_NOT_ALLOWED),

    //    AuthError
    AUTH_001("AUTH_001", "Invalid credentials", HttpStatus.BAD_REQUEST),
    AUTH_002("AUTH_002", "Unauthorized", HttpStatus.UNAUTHORIZED),
    AUTH_003("AUTH_003", "Token expired", HttpStatus.UNAUTHORIZED),
    AUTH_004("AUTH_004", "Token invalid", HttpStatus.UNAUTHORIZED),
    AUTH_005("AUTH_005", "Access denied", HttpStatus.FORBIDDEN),
    AUTH_006("AUTH_006", "Refresh token expired", HttpStatus.UNAUTHORIZED),
    AUTH_007("AUTH_007", "Refresh token invalid", HttpStatus.UNAUTHORIZED),
    AUTH_008("AUTH_008", "Account is locked", HttpStatus.FORBIDDEN),
    AUTH_009("AUTH_009", "Email not verified", HttpStatus.BAD_REQUEST),

    //    UserError
    USER_001("USER_001", "User not found", HttpStatus.NOT_FOUND),
    USER_002("USER_002", "Email already exists", HttpStatus.CONFLICT),
    USER_003("USER_003", "Username already taken", HttpStatus.CONFLICT),
    USER_004("USER_004", "User is inactive", HttpStatus.FORBIDDEN),
    USER_005("USER_005", "Invalid user role", HttpStatus.BAD_REQUEST),
    USER_006("USER_006", "Password incorrect", HttpStatus.BAD_REQUEST),
    USER_007("USER_007", "Cannot update user", HttpStatus.BAD_REQUEST),
    USER_008("USER_008", "User is banned", HttpStatus.FORBIDDEN),
    USER_009("USER_009", "Passwords do not match", HttpStatus.BAD_REQUEST),
    ;


    private final String code;

    private final String message;

    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
