package com.irena.financial_data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Allow access to all endpoints starting with "/api/"
                .allowedOrigins("http://localhost:3000")  // Allow requests from localhost:3000 (your frontend)
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allow only specific HTTP methods
                .allowedHeaders("*");  // Allow any headers
    }
}
