package com.khaling.rest.crud.restfulcrudservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // All request should be authenticated
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        http.sessionManagement(session -> session.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));
        http.httpBasic(Customizer.withDefaults()); //enable basic auth with default Customizations
        // CSFR -> POST, PUT
//        http.csrf(csrf ->
//                // BAD - CSRF protection shouldn't be disabled
//                csrf.disable());

        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        var user = User.withUsername("nabin")
                .password("{noop}dummy")
                .roles(Roles.USER.toString())
                .build();
        var admin = User.withUsername("admin")
                .password("{noop}dummy")
                .roles(Roles.ADMIN.name())
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
