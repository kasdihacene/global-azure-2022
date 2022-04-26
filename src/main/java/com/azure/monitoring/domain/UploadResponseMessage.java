package com.azure.monitoring.domain;

import lombok.Builder;

public record UploadResponseMessage(String message, String status) {

    @Builder
    public UploadResponseMessage {
    }
}
