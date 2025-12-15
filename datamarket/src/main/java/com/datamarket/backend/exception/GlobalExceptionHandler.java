package com.datamarket.backend.exception;

import com.datamarket.backend.dto.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiErrorResponse> handleCustomException(CustomException ex, HttpServletRequest request) {
        ErrorCode errorCode = ex.getErrorCode();

        ApiErrorResponse response = ApiErrorResponse.builder()
                .success(false)
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .status(errorCode.getHttpStatus().value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> validationException(MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ErrorCode errorCode = ErrorCode.COMMON_001;

        ApiErrorResponse response = ApiErrorResponse.builder()
                .success(false)
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .status(errorCode.getHttpStatus().value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .errors(errors)
                .build();

        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneralException(Exception ex, HttpServletRequest request) {
        ErrorCode errorCode = ErrorCode.COMMON_002;

        ApiErrorResponse response = ApiErrorResponse.builder()
                .success(false)
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .status(errorCode.getHttpStatus().value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        ex.printStackTrace();

        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }
}
