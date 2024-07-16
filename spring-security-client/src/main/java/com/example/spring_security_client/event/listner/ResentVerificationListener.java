package com.example.spring_security_client.event.listner;

import com.example.spring_security_client.entity.User;
import com.example.spring_security_client.entity.VerificationToken;
import com.example.spring_security_client.event.ResentVerificationEvent;
import com.example.spring_security_client.repository.VerificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


import java.util.UUID;

@Component
@Slf4j
public class ResentVerificationListener implements ApplicationListener<ResentVerificationEvent> {

    @Autowired
    private VerificationRepository verificationRepository;

    @Override
    public void onApplicationEvent(ResentVerificationEvent event) {
        VerificationToken verificationToken = event.getVerificationToken();

        String token = UUID.randomUUID().toString();

        verificationToken.setToken(token);

        verificationRepository.save(verificationToken);

        // Sent mail to the user
        String url = event.getApplicationUrl() + "/verifyRegistration?token=" + token;
        //sent verficationemail

        log.info("Click here to verify your account {} " + url);
    }
}
