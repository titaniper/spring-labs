package com.example.api;

import com.example.core.CoreConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(CoreConfiguration.class)
@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		// NOTE: 2개 설정
		System.setProperty("spring.config.name", "application-core,application-api");

		SpringApplication.run(ApiApplication.class, args);
	}

}
