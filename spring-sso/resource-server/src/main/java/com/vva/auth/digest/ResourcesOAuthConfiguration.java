package com.vva.auth.digest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
@EnableResourceServer
public class ResourcesOAuthConfiguration extends ResourceServerConfigurerAdapter {

    @Bean
    public RemoteTokenServices remoteTokenServices() {
        final RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
        tokenServices.setClientId("readClientId");
        tokenServices.setClientSecret("test123");
        return tokenServices;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(remoteTokenServices()).resourceId("resource-services");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api").access("#oauth2.hasScope('read')")
                .antMatchers("/todos").access("#oauth2.clientHasRole('USER')")
                .antMatchers("/todos").access("#oauth2.clientHasRole('ADMIN')");
    }
}