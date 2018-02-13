package com.vva.auth.digest;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringBootApplication
@EnableOAuth2Client
public class AuthServerApplication  {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }
}
