package com.vva.auth.digest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableOAuth2Sso
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private OAuth2ClientContext clientContext;

    @Autowired
    OAuth2ClientContext oauth2ClientContext;

    @Autowired
    ResourceServerProperties oauthProperties;

    @Autowired
    private AuthorizationCodeResourceDetails resourceDetails;

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
        UserInfoTokenServices userInfoTokenServices = new UserInfoTokenServices(oauthProperties.getUserInfoUri(), oauthProperties.getClientId());
        userInfoTokenServices.setRestTemplate(restTemplate);
        return userInfoTokenServices;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off

        http.authorizeRequests()
                .antMatchers("/", "/index**", "/login/**", "/webjars/**").permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
                    .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                    .exceptionHandling()
                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/");
//                .and()
//                    .addFilterAt(filter(), BasicAuthenticationFilter.class);
		// @formatter:on
    }
}
