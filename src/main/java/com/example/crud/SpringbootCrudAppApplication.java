package com.example.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SpringbootCrudAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootCrudAppApplication.class, args);
    }

    /**
     * Configures CORS to allow requests from the Angular frontend.
     * This is crucial for local development where frontend and backend run on different ports.
     * In a production environment, you would typically configure this in a reverse proxy (e.g., Nginx).
     * @return WebMvcConfigurer
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Allow all origins, methods, and headers for /api/** endpoints
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:4200") // Angular default port
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
