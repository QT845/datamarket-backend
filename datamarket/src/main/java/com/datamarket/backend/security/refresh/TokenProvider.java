package com.datamarket.backend.security.refresh;

import org.springframework.stereotype.Component;

import java.util.Base64;
import java.security.SecureRandom;

@Component
public class TokenProvider {
    public String generateRefreshToken() {
        byte[] randomBytes = new byte[64];
        new SecureRandom().nextBytes(randomBytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

}
