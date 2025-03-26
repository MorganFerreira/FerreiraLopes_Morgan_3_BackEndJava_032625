package com.p3backEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class P3backEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(P3backEndApplication.class, args);
	}

}
