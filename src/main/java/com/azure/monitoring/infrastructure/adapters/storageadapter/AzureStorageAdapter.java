package com.azure.monitoring.infrastructure.adapters.storageadapter;

import com.azure.monitoring.application.port.out.StoragePort;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
@Component
public class AzureStorageAdapter implements StoragePort {

    private final BlobContainerClient blobContainerClient;

    @Override
    public void store(MultipartFile document) {
        try {
            boolean shouldOverride = true;
            String filename = document.getOriginalFilename();
            BlobClient blobClient = blobContainerClient.getBlobClient(filename);
            blobClient.upload(document.getInputStream(), document.getSize(), shouldOverride);

        } catch (IOException e) {
            throw new AzureStorageUploadException(String.format("Ann error occurred when uploading file [%s]", document.getOriginalFilename()), e.fillInStackTrace());
        }
    }
}
