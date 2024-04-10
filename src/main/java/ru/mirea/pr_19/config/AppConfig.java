package ru.mirea.pr_19.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "ru.mirea.pr_19.repositories")
@EntityScan(basePackages = "ru.mirea.pr_19.models")
@ComponentScan(basePackages = "ru.mirea.pr_19")
public class AppConfig {
}
