package ru.yandex.learn.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("ru.yandex.learn.db.entity")
@EnableJpaRepositories("ru.yandex.learn.db.repository")
public class DbConfig {
}
