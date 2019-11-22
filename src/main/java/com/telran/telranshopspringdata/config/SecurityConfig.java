package com.telran.telranshopspringdata.config;

import com.telran.telranshopspringdata.data.UserDetailsRepository;
import com.telran.telranshopspringdata.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
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

    /**
     * provided singleton of PasswordEncoder to our application
     * @return  PasswordEncoder
     */
    @Bean PasswordEncoder encoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(encoder());
    }

    /**
     * here is some mock filters - check before deploy to production
     */
    @Configuration
    static class AppSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void  configure (HttpSecurity http) throws Exception {
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //stateless - чтоб у нас не было сессий
                    .and().csrf().disable()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/registration").permitAll()
                    .antMatchers(HttpMethod.POST, "/user").authenticated()
                    //.antMatchers(HttpMethod.POST, "/user").permitAll()
                    .antMatchers(HttpMethod.GET, "/products/**", "/categories").permitAll()
                    .antMatchers(HttpMethod.GET, "/user").authenticated()
                    .antMatchers(HttpMethod.GET, "/getall").permitAll()
                    .antMatchers(HttpMethod.GET, "/hellodude").permitAll()
                    .antMatchers("/user/**", "/cart/**","/orders/**", "checkout").hasRole("FULL_USER")
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().denyAll()
                    .and()
                    .httpBasic();
        }
    }

}
