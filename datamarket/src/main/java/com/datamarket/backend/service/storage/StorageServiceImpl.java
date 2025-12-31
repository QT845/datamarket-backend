package com.datamarket.backend.service.storage;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageServiceImpl implements StorageService{
    @Value("${storage.base-path}")
    private String basePath;

    @PostConstruct
    void init() {
        if (basePath == null || basePath.isBlank()) {
            throw new IllegalStateException("storage.base-path is not configured");
        }
    }

    @Override
    public String save(MultipartFile file, String objectKey) throws IOException {
        try {
            Path fullPath = Paths.get(basePath, objectKey);
            Files.createDirectories(fullPath.getParent());
            Files.copy(file.getInputStream(), fullPath, StandardCopyOption.REPLACE_EXISTING);
            return  objectKey;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file",e);
        }
    }

    @Override
    public byte[] read(String objectKey) throws IOException {
        try {
            Path fullPath = Paths.get(basePath, objectKey);
            return Files.readAllBytes(fullPath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file",e);
        }
    }

    @Override
    public void delete(String objectKey) throws IOException {
        try {
            Path fullPath = Paths.get(basePath, objectKey);
            Files.deleteIfExists(fullPath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file",e);
        }
    }
}
