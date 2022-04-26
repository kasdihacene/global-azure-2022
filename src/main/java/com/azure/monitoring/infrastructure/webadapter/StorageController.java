package com.azure.monitoring.infrastructure.webadapter;

import com.azure.monitoring.api.V2Api;
import com.azure.monitoring.application.port.in.StorageUseCase;
import com.azure.monitoring.model.UploadResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@Slf4j
public record StorageController(StorageUseCase storageUseCase, StorageMapper storageMapper) implements V2Api {

    @ApiOperation(value = "Upload document to Azure Storage", nickname = "uploadDocument", notes = "Upload multipart file scenario", response = UploadResponse.class, tags = {"cloud_native_app_upload",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response", response = UploadResponse.class)})
    @RequestMapping(value = "/v2/upload",
            produces = {"application/json"},
            consumes = {"multipart/form-data"},
            method = RequestMethod.POST)
    @Override
    public ResponseEntity<UploadResponse> uploadDocument(@Valid @RequestPart(value = "document") MultipartFile document) {
        return new ResponseEntity<>(storageMapper.map(storageUseCase.upload(document)), HttpStatus.OK);
    }

}
