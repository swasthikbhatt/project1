package com.example.project1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer
{
    private final String[] allowedOrigins;

    // Constrocutor injection to get the allowed origins from application.properties, constructor injection is preferred over field injection for better testability and immutability.
    public CorsConfig(@Value("${app.cors.allowed-origins}") String[] allowedOrigins)
    {
        this.allowedOrigins = allowedOrigins;
    }

    /**
     * Configure CORS mappings for the application.
     * Your React code sends a request, Your Spring Boot controller returns a response, Spring Boot sends "Hello, World!" back to the browser.
     * Your browser receives the response. React's JavaScript wants to read the returned text and display it on the page.
     * When CORS allows the request, React can read the response:React reads the text and displays it.
     * If the origin isn't allowed, the browser may block JavaScript from reading the response:
     * If CORS doesn't allow the origin --> Spring Boot might still process the request and return "Hello, World!", but the browser prevents the requesting JavaScript from accessing that response. React gets a CORS error.
     * Imagine a malicious website running in your browser tries to contact another website where you're logged in and read private information.
     * CORS is one browser security mechanism that helps prevent a website from reading responses from other origins unless the responding server permits it.
     * addMapping("/**") applies the CORS rules to all Spring MVC URL paths.
     * allowedOrigins(allowedOrigins) permits each configured origin.
     * allowedMethods(...) specifies the HTTP methods allowed for cross-origin requests.
     * allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"); means React sends a GET request to fetch data. GET is allowed by your configuration. React sends a POST request to submit data. POST is allowed too. If React sends a DELETE request, that method is also permitted by the CORS rules.
     * @param registry the CorsRegistry to configure
     */
    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
    }
}