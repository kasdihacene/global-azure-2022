package com.azure.monitoring.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "post")
@Configuration
@Getter
@Setter
public class PostProperties {
    private String urlGetAll;
    private String baseUrl;
}
