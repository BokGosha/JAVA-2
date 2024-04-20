package ru.mirea.pr_19.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableJpaRepositories(basePackages = "ru.mirea.pr_19.repositories")
@EntityScan(basePackages = "ru.mirea.pr_19.entities")
@ComponentScan(basePackages = "ru.mirea.pr_19")
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy
@EnableWebSecurity
public class AppConfig {
}
