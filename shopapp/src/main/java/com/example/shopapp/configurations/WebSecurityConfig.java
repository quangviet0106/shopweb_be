package com.example.shopapp.configurations;

import com.example.shopapp.constants.Constants;
import com.example.shopapp.filters.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request->{
                    request.requestMatchers(Constants.API_VERSION+"/users/register", Constants.API_VERSION+"/users/login")
                            .permitAll()
                            .requestMatchers(GET,Constants.API_VERSION+"/categories**").hasAnyRole(Constants.USER,Constants.ADMIN)
                            .requestMatchers(POST,Constants.API_VERSION+"/categories/**").hasAnyRole(Constants.ADMIN)
                            .requestMatchers(PUT,Constants.API_VERSION+"/categories/**").hasAnyRole(Constants.ADMIN)
                            .requestMatchers(DELETE,Constants.API_VERSION+"/categories/**").hasAnyRole(Constants.ADMIN)

                            .requestMatchers(GET,Constants.API_VERSION+"/products**").hasAnyRole(Constants.USER,Constants.ADMIN)
                            .requestMatchers(POST,Constants.API_VERSION+"/products/**").hasAnyRole(Constants.ADMIN)
                            .requestMatchers(PUT,Constants.API_VERSION+"/products/**").hasAnyRole(Constants.ADMIN)
                            .requestMatchers(DELETE,Constants.API_VERSION+"/products/**").hasAnyRole(Constants.ADMIN)

                            .requestMatchers(POST,Constants.API_VERSION+"/order_details/**").hasAnyRole(Constants.USER,Constants.ADMIN)
                            .requestMatchers(GET, Constants.API_VERSION+"/order_details/**").hasAnyRole(Constants.USER,Constants.ADMIN)
                            .requestMatchers(PUT, Constants.API_VERSION+"/order_details/**").hasRole(Constants.ADMIN)
                            .requestMatchers(DELETE, Constants.API_VERSION+"/order_details/**").hasRole(Constants.ADMIN)

                            .requestMatchers(POST,Constants.API_VERSION+"/orders/**").hasAnyRole(Constants.USER,Constants.ADMIN)
                            .requestMatchers(GET, Constants.API_VERSION+"/orders/**").hasAnyRole(Constants.USER,Constants.ADMIN)
                            .requestMatchers(PUT, Constants.API_VERSION+"/orders/**").hasRole(Constants.ADMIN)
                            .requestMatchers(DELETE, Constants.API_VERSION+"/orders/**").hasRole(Constants.ADMIN)
                            .anyRequest().authenticated();

                });
        return httpSecurity.build();
    }
}
