package com.vva.auth.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.vva.auth.basic"})
public class ApiBasicApplication {


	public static void main(String[] args) {
		SpringApplication.run(ApiBasicApplication.class, args);
	}
}
