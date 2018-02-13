package com.vva.auth.digest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.vva.auth.digest"})
public class ApiDigestApplication {


	public static void main(String[] args) {
		SpringApplication.run(ApiDigestApplication.class, args);
	}
}
