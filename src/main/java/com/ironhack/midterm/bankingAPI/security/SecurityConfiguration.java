package com.ironhack.midterm.bankingAPI.security;
import com.ironhack.midterm.bankingAPI.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.csrf().disable();
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/api/v1/admin/**").hasAnyRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/api/v1/admin/**").hasAnyRole("ADMIN")
                .mvcMatchers(HttpMethod.PATCH, "/api/v1/admin/**").hasAnyRole("ADMIN")
                .mvcMatchers(HttpMethod.PUT, "/api/v1/admin/**").hasAnyRole("ADMIN")
                .mvcMatchers(HttpMethod.GET, "/api/v1/my_accounts").hasAnyRole("ACCOUNT_HOLDER")
                .mvcMatchers(HttpMethod.POST, "/api/v1/transfer_funds").hasAnyRole("ACCOUNT_HOLDER")
                .mvcMatchers(HttpMethod.POST, "/api/v1/third_party/**").hasAnyRole("THIRD_PARTY");
    }
}