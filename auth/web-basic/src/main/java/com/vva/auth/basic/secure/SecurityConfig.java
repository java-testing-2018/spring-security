package com.vva.auth.digest.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfLogoutHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // @formatter:off
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CookieCsrfTokenRepository csrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();

        http
                .csrf().csrfTokenRepository(csrfTokenRepository).and()
//                .csrf().disable()

                .authorizeRequests()
                    .antMatchers("/static/css/**", "/index").permitAll()
                    .antMatchers("/user/**").hasRole("USER")
//                               .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                    .and()

                .logout()
                    .logoutUrl("/logout")
                    .addLogoutHandler(new CsrfLogoutHandler(csrfTokenRepository))
//                    .deleteCookies(cookieNamesToClear)
                    .invalidateHttpSession(true)
                    .and()
                .formLogin()
                    .loginPage("/login")

                .failureUrl("/login-error");
    }
    // @formatter:on

    // @formatter:off
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("123").roles("USER").and()
                .withUser("admin").password("123").roles("ADMIN");
    }
    // @formatter:on
}
