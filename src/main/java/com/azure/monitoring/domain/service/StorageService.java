package com.azure.monitoring.domain.service;

import com.azure.monitoring.application.port.in.StorageUseCase;
import com.azure.monitoring.application.port.out.StoragePort;
import com.azure.monitoring.domain.UploadResponseMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@AllArgsConstructor
public class StorageService implements StorageUseCase {

    private final StoragePort storagePort;

    @Override
    public UploadResponseMessage upload(MultipartFile document) {
        storagePort.store(document);
        return UploadResponseMessage.builder()
                .message(String.format("File [%s] Correctly uploaded", document.getOriginalFilename()))
                .status("SUCCESS")
                .build();
    }
}
