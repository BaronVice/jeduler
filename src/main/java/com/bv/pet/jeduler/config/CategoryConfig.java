package com.bv.pet.jeduler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class CategoryConfig {
    @Bean
    public List<Short> categoryPerUserList(){
        return Collections.synchronizedList(
                new ArrayList<>(Collections.nCopies(Short.MAX_VALUE+1, (short) 0))
        );
    }
}
