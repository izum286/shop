package com.telran.telranshopspringdata.config;

import com.telran.telranshopspringdata.data.UserDetailsRepository;
import com.telran.telranshopspringdata.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    UserDetailsRepository repository;

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsService(repository);
    }

    @Bean PasswordEncoder encoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder)
}
