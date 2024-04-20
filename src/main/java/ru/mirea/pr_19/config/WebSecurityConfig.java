package ru.mirea.pr_19.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityDataConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import ru.mirea.pr_19.repositories.UserRepository;
import ru.mirea.pr_19.services.UserService;
import ru.mirea.pr_19.services.impl.UserServiceImpl;

@RequiredArgsConstructor
@Configuration
@Import(SecurityDataConfiguration.class)
@EnableWebSecurity
public class WebSecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final SessionRegistry sessionRegistry;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth, UserService userService) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/home").permitAll()
                                .requestMatchers("/login**").permitAll()
                                .requestMatchers("/register**").permitAll()
                                .requestMatchers("/**").hasRole("USER")
                )
                .formLogin(Customizer.withDefaults())
                .sessionManagement(
                        session -> session
                                .maximumSessions(3)
                                .maxSessionsPreventsLogin(true)
                                .sessionRegistry(sessionRegistry)
                ).build();
    }

    @Bean
    public static SessionRegistry sessionRegistry(JdbcIndexedSessionRepository sessionRepository) {
        return new SpringSessionBackedSessionRegistry<>(sessionRepository);
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public static UserService userService(UserRepository users) {
        return new UserServiceImpl(passwordEncoder(), users);
    }
}
