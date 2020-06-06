package com.sin.orb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/taskcards/**")
                .allowedMethods("*");
        registry.addMapping("/user/me")
                .allowedMethods("GET");
        registry.addMapping("/auth/*")
                .allowedMethods("POST");
    }
}