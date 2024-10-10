package com.bv.pet.jeduler.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "custom.firebase")
public class FirebaseConfigurationProperties {
    private String path;
}
