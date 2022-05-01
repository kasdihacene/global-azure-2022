package com.azure.monitoring.infrastructure.adapters.webadapter;


import com.azure.monitoring.domain.UploadResponseMessage;
import com.azure.monitoring.model.UploadResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StorageMapper {

    UploadResponse map(UploadResponseMessage upload);
}
