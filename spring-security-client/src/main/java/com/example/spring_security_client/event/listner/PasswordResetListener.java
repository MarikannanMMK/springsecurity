package com.example.spring_security_client.event.listner;

import com.example.spring_security_client.entity.User;
import com.example.spring_security_client.event.PasswordRestEvent;
import com.example.spring_security_client.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class PasswordResetListener implements ApplicationListener<PasswordRestEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(PasswordRestEvent event) {
        User user = event.getUser();

        String token = UUID.randomUUID().toString();

        userService.savePasswordResetToken(user,token);

        String url = event.getApplicationUrl() + "/savePassword?token=" + token;

        //sent Password reset email

        log.info("Click here to reset your account password {} " + url);




    }
}
