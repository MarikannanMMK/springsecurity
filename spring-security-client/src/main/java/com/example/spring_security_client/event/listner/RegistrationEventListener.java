package com.example.spring_security_client.event.listner;

import com.example.spring_security_client.entity.User;
import com.example.spring_security_client.event.RegistrationEvent;
import com.example.spring_security_client.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationEventListener implements ApplicationListener<RegistrationEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationEvent event) {


        // Create verification token for the user with link
        User user = event.getUser();

        String token = UUID.randomUUID().toString();

        userService.SaveVerificationTokenWithUser(user, token);

        // Sent mail to the user

        String url = event.getApplicationUrl() + "/verifyRegistration?token=" + token;

        //sent verficationemail

        log.info("Click here to verify your account {} " + url);


    }
}
