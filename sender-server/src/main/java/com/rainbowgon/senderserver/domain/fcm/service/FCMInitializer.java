package com.rainbowgon.senderserver.domain.fcm.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.gson.stream.JsonReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;

@Component
@Slf4j
public class FCMInitializer {

    @Value("${fcm.certification}")
    private String credential;


    @PostConstruct
    public void initialize() {
        ClassPathResource resource = new ClassPathResource(credential);

        try {
            JsonReader jsonReader = new JsonReader(new FileReader(resource.getFile()));
            jsonReader.setLenient(true);

            File file = new File(resource.getPath());
            FileWriter fileWriter = new FileWriter("fcm-token.json");
            fileWriter.write(jsonReader.toString());
            fileWriter.flush();
            fileWriter.close();

            InputStream stream = resource.getInputStream();

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(stream))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("FirebaseApp initialization complete");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}