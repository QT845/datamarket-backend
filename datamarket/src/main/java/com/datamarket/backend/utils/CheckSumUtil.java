package com.datamarket.backend.utils;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.MessageDigest;
@Component
public class CheckSumUtil {
    public static String sha256(InputStream inputStream) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
            return bytesToHex(digest.digest());
        } catch (Exception e) {
            throw new RuntimeException("Failed to compute SHA-256 checksum", e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hex = new StringBuilder();
        for (byte b : hash) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }
}
