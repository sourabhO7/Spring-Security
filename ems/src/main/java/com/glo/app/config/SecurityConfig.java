package com.glo.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    //PasswordEncoder using base64 format
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //bean declaration of the authentication manager is important
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    //http basic authentication logic using SecurityFilterChain
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(
                        (authorize)->authorize.requestMatchers(HttpMethod.GET,"/api/employees/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
                                .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults());
        return http.build();
    }

    //In memory authentication using UserDetailsService
//    @Bean
//    UserDetailsService userDetailsService(){
//        UserDetails sourabh = User.builder().username("Sourabh").password(passwordEncoder().encode("Sourabh")).roles("USER").build();
//        UserDetails admin = User.builder().username("Admin").password(passwordEncoder().encode("Admin")).roles("ADMIN").build();
//
//        return new InMemoryUserDetailsManager(sourabh,admin);
//    }

}
