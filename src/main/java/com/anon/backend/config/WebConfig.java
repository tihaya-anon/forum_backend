package com.anon.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**") // Allow CORS on all paths
        .allowedOrigins("http://localhost:3000") // Allow requests from this origin
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Specify allowed methods
        .allowedHeaders("*") // Allow all headers
        .allowCredentials(true); // Allow cookies if needed
  }
}
