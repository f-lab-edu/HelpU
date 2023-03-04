package com.flab.helpu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <strong> Annotation Note : </strong>
 * 내부에는 @SpringBootConfiguration, @EnableAutoConfiguration, @ComponentScan 이 존재한다고 한다
 * 앱이 자동 구성을 사용하고, 애플리케이션 클래스에서 추가 구성을 정의할 수 있는 앱을 개발자들이 좋아한다
 * @EnableAutoConfiguration : Spring Boot의 자동설정 매커니즘을 사용할 수 있다.
 * @ComponentScan : 패키지에서 @Component 어노테이션이 붙은 클래스들에 대한 스캐닝을 활성화.
 * @SpringBootConfiguration : 빈을 등록하거나 추가 구성 클래스를 가져올 수 있다.
 */
@SpringBootApplication
public class HelpuApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelpuApplication.class, args);
	}

}
