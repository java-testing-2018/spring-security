package com.vva.auth.digest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private OAuth2ClientContext clientContext;

    @Autowired
    OAuth2ClientContext oauth2ClientContext;

    @Autowired
    ResourceServerProperties oauthProperties;

    @Autowired
    private ClientCredentialsResourceDetails resourceDetails;

    @Autowired
    private OAuth2RestTemplate restTemplate;

    @Autowired
    private UserInfoTokenServices tokenServices;

    @Bean
    OAuth2RestTemplate oAuth2RestTemplate() {
        return new OAuth2RestTemplate(resourceDetails, clientContext);
    }

    @Bean
    UserInfoTokenServices userInfoTokenServices() {
        return new UserInfoTokenServices(oauthProperties.getUserInfoUri(), oauthProperties.getClientId());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off

        http.authorizeRequests()
//                .antMatchers("/drive-list").permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
                    .csrf().disable()
                .exceptionHandling()

                .and()
                    .httpBasic()
                .and()
                    .addFilterAt(filter(), BasicAuthenticationFilter.class);
		// @formatter:on
    }

    private Filter filter() {
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter("/drive-list");

        filter.setRestTemplate(restTemplate);
        filter.setTokenServices(tokenServices);

        return filter;
    }
}
