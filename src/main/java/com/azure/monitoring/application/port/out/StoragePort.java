package com.azure.monitoring.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface StoragePort {
    void store(MultipartFile document);
}
