package com.azure.monitoring.application.port.in;

import com.azure.monitoring.domain.UploadResponseMessage;
import org.springframework.web.multipart.MultipartFile;

public interface StorageUseCase {

    UploadResponseMessage upload(MultipartFile document);
}
