package com.bv.pet.jeduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
@EnableAsync
@EnableWebSecurity
@EnableTransactionManagement
@EnableConfigurationProperties()
public class JedulerApplication {
	public static void main(String[] args) {
		SpringApplication.run(JedulerApplication.class, args);
	}
}
