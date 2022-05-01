package com.azure.monitoring.infrastructure.adapters.storageadapter;

public class AzureStorageUploadException extends RuntimeException {

    public AzureStorageUploadException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
