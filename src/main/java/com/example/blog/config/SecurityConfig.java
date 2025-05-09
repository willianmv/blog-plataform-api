package com.example.blog.config;

import com.example.blog.security.JwtAuthenticationFilter;
import com.example.blog.services.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception{
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/v1/categories").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/categories").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/tags").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/tags").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/tags/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/roles").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/roles").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/roles/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/posts").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/posts/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/posts/drafts").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/posts").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/posts/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/posts/**").hasRole("USER")

                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return  httpSecurity.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationService authenticationService){
        return new JwtAuthenticationFilter(authenticationService);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
