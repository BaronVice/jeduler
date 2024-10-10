package com.bv.pet.jeduler.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(FirebaseConfigurationProperties.class)
public class FirebaseConfig {
    private final FirebaseConfigurationProperties configurationProperties;

    @Bean
    public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp){
        return FirebaseMessaging.getInstance(firebaseApp);
    }

    @SneakyThrows
    @Bean
    public FirebaseApp firebaseApp(){
        FileInputStream serviceAccount = new FileInputStream(
                configurationProperties.getPath()
        );
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(
                        GoogleCredentials.fromStream(serviceAccount)
                )
                .build();


        return FirebaseApp.initializeApp(options);
    }
}
