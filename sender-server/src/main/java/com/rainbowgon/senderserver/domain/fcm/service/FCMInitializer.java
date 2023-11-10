package com.rainbowgon.senderserver.domain.fcm.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.rainbowgon.senderserver.global.error.exception.FCMInitializerFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;

@Component
@Slf4j
public class FCMInitializer {

    @Value("${fcm.certification}")
    private String credential;

    @Value("${fcm.project-id}")
    private String projectId;

    @PostConstruct
    public void initialize() {
        ClassPathResource resource = new ClassPathResource(credential);

        try (InputStream stream = resource.getInputStream()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(stream))
                    .setProjectId(projectId)
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("FirebaseApp initialization complete");
            }
        } catch (Exception e) {
            System.out.println("================================");
            e.printStackTrace();
            System.out.println("================================");

            throw FCMInitializerFailException.EXCEPTION;
        }
    }
}