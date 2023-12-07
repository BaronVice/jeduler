package com.bv.pet.jeduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableWebSecurity
@EnableConfigurationProperties()
public class JedulerApplication {
	public static void main(String[] args) {
		SpringApplication.run(JedulerApplication.class, args);
	}
}
