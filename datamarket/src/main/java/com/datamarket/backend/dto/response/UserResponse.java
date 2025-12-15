package com.datamarket.backend.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
    Long id;
    String email;
    String fullName;
    String role;
}
