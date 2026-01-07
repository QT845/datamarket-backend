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
    // Validation / Request
    COMMON_010("COMMON_010", "Validation failed", HttpStatus.BAD_REQUEST),
    COMMON_011("COMMON_011", "Required field is missing", HttpStatus.BAD_REQUEST),
    COMMON_012("COMMON_012", "Field value is invalid", HttpStatus.BAD_REQUEST),
    COMMON_013("COMMON_013", "Field value is blank", HttpStatus.BAD_REQUEST),
    COMMON_014("COMMON_014", "Field length is invalid", HttpStatus.BAD_REQUEST),
    COMMON_015("COMMON_015", "File is empty", HttpStatus.BAD_REQUEST),
    COMMON_016("COMMON_016", "File read failed", HttpStatus.BAD_REQUEST),
    COMMON_017("COMMON_017", "File save failed", HttpStatus.BAD_REQUEST),




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
    AUTH_011("AUTH_011", "You are not permission to access", HttpStatus.BAD_REQUEST),

    //    UserError
    USER_001("USER_001", "User not found", HttpStatus.NOT_FOUND),
    USER_002("USER_002", "Email already exists", HttpStatus.CONFLICT),
    USER_003("USER_003", "User is inactive", HttpStatus.FORBIDDEN),
    USER_004("USER_004", "Invalid user role", HttpStatus.BAD_REQUEST),
    USER_005("USER_005", "Password incorrect", HttpStatus.BAD_REQUEST),
    USER_006("USER_006", "Cannot update user", HttpStatus.BAD_REQUEST),
    USER_007("USER_007", "User is banned", HttpStatus.FORBIDDEN),
    USER_008("USER_008", "Passwords do not match", HttpStatus.BAD_REQUEST),

    //   ProviderError
    PROVIDER_001("PROVIDER_001", "Provider not found", HttpStatus.NOT_FOUND),
    PROVIDER_002("PROVIDER_002", "Provider already exists", HttpStatus.CONFLICT),
    PROVIDER_003("PROVIDER_003", "Provider is not approved", HttpStatus.FORBIDDEN),
    PROVIDER_004("PROVIDER_004", "Cannot update provider", HttpStatus.BAD_REQUEST),


    // DatasetError
    // Dataset - General (001–009)
    // Dataset - Domain & Policy (010–019)
    // Dataset - Version (020–029)
    // Dataset - Validation (030–039)
    // Dataset - Review (040–049)
    // Dataset - Permission (050–059)

    DATASET_001("DATASET_001", "Dataset not found", HttpStatus.NOT_FOUND),
    DATASET_002("DATASET_002", "Dataset already exists", HttpStatus.CONFLICT),
    DATASET_003("DATASET_003", "Dataset is not editable", HttpStatus.BAD_REQUEST),
    DATASET_004("DATASET_004", "Dataset is not in draft status", HttpStatus.BAD_REQUEST),
    DATASET_005("DATASET_005", "Dataset is not approved", HttpStatus.BAD_REQUEST),
    DATASET_006("DATASET_006", "Dataset does not allow uploading version", HttpStatus.BAD_REQUEST),
    DATASET_007("DATASET_007", "Dataset ownership is invalid", HttpStatus.FORBIDDEN),

    DATASET_010("DATASET_010", "Dataset domain not found", HttpStatus.NOT_FOUND),
    DATASET_011("DATASET_011", "Dataset domain is inactive", HttpStatus.BAD_REQUEST),
    DATASET_012("DATASET_012", "Dataset type is not allowed for this domain", HttpStatus.BAD_REQUEST),
    DATASET_020("DATASET_020", "Dataset version not found", HttpStatus.NOT_FOUND),
    DATASET_021("DATASET_021", "Dataset version already exists", HttpStatus.CONFLICT),
    DATASET_022("DATASET_022", "Duplicated file", HttpStatus.BAD_REQUEST),
    DATASET_023("DATASET_023", "Dataset version is not pending review", HttpStatus.BAD_REQUEST),
    DATASET_024("DATASET_024", "Dataset version is already approved", HttpStatus.CONFLICT),
    DATASET_025("DATASET_025", "Dataset version is already rejected", HttpStatus.CONFLICT),
    DATASET_026("DATASET_026", "Dataset version is not created", HttpStatus.CONFLICT),
    DATASET_027("DATASET_027", "Dataset version checksum mismatch", HttpStatus.BAD_REQUEST),
    DATASET_028("DATASET_028", "Dataset version does not belong to dataset", HttpStatus.BAD_REQUEST),
    DATASET_029("DATASET_029", "Dataset version not approved", HttpStatus.BAD_REQUEST),
    DATASET_030("DATASET_030", "Dataset upload failed", HttpStatus.BAD_REQUEST),
    DATASET_031("DATASET_031", "Dataset format is invalid", HttpStatus.BAD_REQUEST),
    DATASET_032("DATASET_032", "Dataset validation failed", HttpStatus.BAD_REQUEST),
    DATASET_033("DATASET_033", "Dataset rejected by system", HttpStatus.BAD_REQUEST),
    DATASET_040("DATASET_040", "Dataset version approval failed", HttpStatus.BAD_REQUEST),
    DATASET_041("DATASET_041", "Dataset version rejected by moderator", HttpStatus.BAD_REQUEST),
    DATASET_042("DATASET_042", "Moderator is not allowed to review dataset", HttpStatus.FORBIDDEN),
    DATASET_043("DATASET_043", "Dataset Report not found", HttpStatus.FORBIDDEN),
    DATASET_050("DATASET_050", "User is not dataset owner", HttpStatus.FORBIDDEN),
    DATASET_051("DATASET_051", "User is not allowed to upload dataset", HttpStatus.FORBIDDEN),



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
