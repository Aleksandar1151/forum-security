package com.forumsecurity.forum.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class WebSecurityConfig {

    private final JWTFilter jwtFilter;

    public WebSecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    // Bean for Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean for AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Bean for SecurityFilterChain to replace deprecated WebSecurityConfigurerAdapter
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {



        http

                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(
                                        "/api/forums/login",
                                        "/api/forums/admin/users",
                                        "/api/forums/validateToken",
                                        "/api/forums/sendmail",
                                        "/api/forums/sendmail/*"
                                        //,"/api/forums"

                                ).permitAll() // Public routes
                                .anyRequest().authenticated() // All other routes require authentication
                )
                .sessionManagement(session -> session.disable()) // Disable session management (stateless)
                .formLogin(form -> form.disable()) // Disable form login
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter
            .cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource())); // Apply CORS
        return http.build();
    }

    // Bean for CORS configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Set the Angular frontend URL
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true); // Allow credentials like Authorization headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration); // Apply the CORS config to your API paths
        return source;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:4200") // Replace with your Angular app's origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true); // If your API uses credentials (e.g., cookies, authorization headers)
            }
        };
    }
}