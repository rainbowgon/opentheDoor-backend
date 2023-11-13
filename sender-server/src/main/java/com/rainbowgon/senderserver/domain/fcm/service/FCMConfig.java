//package com.rainbowgon.senderserver.domain.fcm.service;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.rainbowgon.senderserver.global.error.exception.FCMFileNotFoundException;
//import com.rainbowgon.senderserver.global.error.exception.FCMInitializerFailException;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.List;
//
//@Configuration
//public class FCMConfig {
//
//    @Value("${fcm.certification}")
//    private String credential;
//
//    @Bean
//    FirebaseMessaging firebaseMessaging() {
//        try {
//            ClassPathResource resource = new ClassPathResource(credential);
//            InputStream credentialStream = null;
//            credentialStream = resource.getInputStream();
//
//            FirebaseApp firebaseApp = null;
//            List<FirebaseApp> firebaseAppList = FirebaseApp.getApps();
//
//            if (firebaseAppList != null && !firebaseAppList.isEmpty()) {
//                for (FirebaseApp app : firebaseAppList) {
//                    if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) {
//                        firebaseApp = app;
//                    }
//                }
//            } else {
//                FirebaseOptions options = FirebaseOptions.builder()
//                        .setCredentials(GoogleCredentials.fromStream(credentialStream))
//                        .build();
//                firebaseApp = FirebaseApp.initializeApp(options);
//            }
//
//            return FirebaseMessaging.getInstance(firebaseApp);
//
//        } catch (FileNotFoundException e) {
//            System.out.println("==============================111==");
//            e.printStackTrace();
//            System.out.println("==============================111==");
//            throw FCMFileNotFoundException.EXCEPTION;
//        } catch (IOException e) {
//            System.out.println("==============================222==");
//            e.printStackTrace();
//            System.out.println("==============================222==");
//            throw FCMInitializerFailException.EXCEPTION;
//        }
//    }
//}