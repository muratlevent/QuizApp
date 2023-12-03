package com.example.QuizGame;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize ->
                        authorize
                                .requestMatchers("/login", "/register", "/home").permitAll() // Bu URL'ler herkese açık
                                .anyRequest().authenticated() // Diğer tüm istekler için kimlik doğrulaması gerektirir
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login") // Özel giriş sayfası
                                .defaultSuccessUrl("/start", true) // Girişten sonra /start'a yönlendirme
                                .permitAll()
                )
                .logout(logout ->
                        logout.permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
