package com.example.core;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @EnableJpaAuditing
 * - JPA
 * @ComponentScan
 * - 스프링이 관리할 컴포넌트를 자동으로 찾아내어 스프링 컨테이너에 등록합니다.
 * - 스프링 프레임워크에게 지정된 패키지 이하에서 컴포넌트(Component),
 * 서비스(Service), 컨피규레이션(Configuration), 리포지토리(Repository) 등 스프링이 관리하는 객체를 스캔하라고 지시합니다.
 * 이를 통해 개발자는 별도의 빈(Bean) 등록 과정 없이 자동으로 의존성 주입(Dependency Injection)을 받을 수 있습니다.
 * @EnableAutoConfiguration
 * - 스프링 부트가 클래스패스와 기타 빈을 확인하여 적합한 자동 구성을 제공합니다.
 * - 상의 라이브러리와 어플리케이션에 정의된 빈들을 기반으로, 필요한 구성을 자동으로 설정
 */
@EnableJpaAuditing
@ComponentScan
@EnableAutoConfiguration
public class CoreConfiguration {
}
