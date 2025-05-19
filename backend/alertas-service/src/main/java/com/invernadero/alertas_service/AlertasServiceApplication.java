package com.invernadero.alertas_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.invernadero.alertas_service.model")
public class AlertasServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlertasServiceApplication.class, args);
	}

}
