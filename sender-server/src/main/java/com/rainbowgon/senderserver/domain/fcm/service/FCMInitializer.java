package com.rainbowgon.senderserver.domain.fcm.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.rainbowgon.senderserver.global.error.exception.FCMFileNotFoundException;
import com.rainbowgon.senderserver.global.error.exception.FCMInitializerFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
public class FCMInitializer {

    @Value("${fcm.certification}")
    private String credential;

    @PostConstruct
    public void initialize() {

        try {
            ClassPathResource resource = new ClassPathResource(credential);
            InputStream stream = resource.getInputStream();

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(stream))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("FirebaseApp initialization complete");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw FCMFileNotFoundException.EXCEPTION;
        } catch (IOException e) {
            e.printStackTrace();
            throw FCMInitializerFailException.EXCEPTION;
        }

    }
}