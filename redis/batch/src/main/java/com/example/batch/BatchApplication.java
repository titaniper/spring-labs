package com.example.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.batch")
//@EnableBatchProcessing
public class BatchApplication {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application-batch");
        SpringApplication.run(BatchApplication.class, args);
    }
}