package springboot.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF so Postman can send POST/PUT requests
                .csrf(csrf -> csrf.disable())

                // 2. Allow all requests without a password (for now)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )

                // 3. Keep the frame options compatible (useful if using H2 console later)
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}
