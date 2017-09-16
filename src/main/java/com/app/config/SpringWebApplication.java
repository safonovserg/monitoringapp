package com.app.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Sergey on 13.09.2017.
 */
@SpringBootApplication
@ComponentScan("com.app")
@EntityScan("com.app.model")
@EnableJpaRepositories("com.app.repository")
public class SpringWebApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringWebApplication.class, args);
    }
}
