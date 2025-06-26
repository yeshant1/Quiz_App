package com.lpu.payment_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/quiz/**")
                .allowedOrigins("http://localhost:8090") // Adjust for  frontend URL
                .allowedMethods("GET");
    }
}

//WebMvcConfigurer -> interface to customize MVC behavior without full override.