package com.example.consumer;

import com.example.core.CoreConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

// NOTE: Core 모듈 사용
@Import(CoreConfiguration.class)
@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        // NOTE: 2개 설정
        System.setProperty("spring.config.name", "application-core,application-consumer");

        SpringApplication.run(ConsumerApplication.class, args);
    }

}
