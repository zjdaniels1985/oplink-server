package com.oplink.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Value("${cors.allowed-origins}")
    private String allowedOrigins;
    
    @Value("${cors.allowed-methods}")
    private String allowedMethods;
    
    @Value("${cors.allowed-headers}")
    private String allowedHeaders;
    
    @Value("${cors.allow-credentials}")
    private boolean allowCredentials;
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] origins = allowedOrigins != null && !allowedOrigins.trim().isEmpty() 
            ? allowedOrigins.split(",") 
            : new String[]{};
        String[] methods = allowedMethods != null && !allowedMethods.trim().isEmpty() 
            ? allowedMethods.split(",") 
            : new String[]{"GET", "POST", "PUT", "DELETE", "OPTIONS"};
        String[] headers = allowedHeaders != null && !allowedHeaders.trim().isEmpty() 
            ? allowedHeaders.split(",") 
            : new String[]{"*"};
        
        registry.addMapping("/api/**")
                .allowedOrigins(origins)
                .allowedMethods(methods)
                .allowedHeaders(headers)
                .allowCredentials(allowCredentials);
        
        registry.addMapping("/ws/**")
                .allowedOrigins(origins)
                .allowedMethods(methods)
                .allowedHeaders(headers)
                .allowCredentials(allowCredentials);
    }
}
