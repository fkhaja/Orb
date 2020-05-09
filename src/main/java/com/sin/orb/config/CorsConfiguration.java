package com.sin.orb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer
{
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/taskcards")
                .allowedMethods("*");
        registry.addMapping("/taskcards/*/tasks")
                .allowedMethods("*");
        registry.addMapping("/user/me")
                .allowedMethods("GET");
        registry.addMapping("/auth/*")
                .allowedMethods("POST");
    }
}