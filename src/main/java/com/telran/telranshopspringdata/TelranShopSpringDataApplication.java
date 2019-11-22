package com.telran.telranshopspringdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
/**
 * extends SpringBootServletInitializer
 * inserted- to be runned in tomcat
 */
public class TelranShopSpringDataApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TelranShopSpringDataApplication.class, args);
    }

}
