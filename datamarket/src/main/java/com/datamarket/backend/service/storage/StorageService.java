package com.datamarket.backend.service.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    String save(MultipartFile file, String objectKey) throws IOException;
    byte[] read(String objectKey) throws IOException;
    void delete(String objectKey) throws IOException;
}
