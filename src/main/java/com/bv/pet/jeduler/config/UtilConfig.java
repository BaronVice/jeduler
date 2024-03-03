package com.bv.pet.jeduler.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class UtilConfig {

    @Bean
    @Scope("prototype")
    public Faker faker(){
        return new Faker();
    }
}
