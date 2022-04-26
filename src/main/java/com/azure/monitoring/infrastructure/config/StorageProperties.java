package com.azure.monitoring.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "azure.storage")
@Configuration
@Getter
@Setter
public class StorageProperties {

    private String connectionString;
    private String containerName;
    private String endpoint;
}
