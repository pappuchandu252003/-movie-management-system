package com.moviemanagement.config;

import com.moviemanagement.security.JwtFilter;
import com.moviemanagement.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        //  PUBLIC APIs
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/**").permitAll() // only register

                        //  ADMIN ONLY MODULES
                        .requestMatchers("/movies/**", "/theatres/**", "/shows/**")
                        .hasRole("ADMIN")

                        //  BOOKINGS (USER + ADMIN)
                        .requestMatchers("/bookings/**")
                        .hasAnyRole("USER", "ADMIN")

                        //  REVIEWS (READ)
                        .requestMatchers(HttpMethod.GET, "/reviews/**")
                        .hasAnyRole("USER", "ADMIN")

                        //  REVIEWS (WRITE)
                        .requestMatchers("/reviews/**")
                        .hasRole("USER")

                        //  EVERYTHING ELSE
                        .anyRequest().authenticated()
                )

                //  AUTH PROVIDER
                .authenticationProvider(authenticationProvider());

        // JWT FILTER
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    //  AUTH PROVIDER
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    //  PASSWORD ENCODER
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //  AUTH MANAGER
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}