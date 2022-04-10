package com.azure.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GlobalAzure2022Application {

	public static void main(String[] args) {
		SpringApplication.run(GlobalAzure2022Application.class, args);
	}

}
