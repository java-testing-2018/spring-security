package com.vva.auth.digest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class OAuthAuthorizationJwtServer extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(OAuthAuthorizationJwtServer.class, args);
    }
}
