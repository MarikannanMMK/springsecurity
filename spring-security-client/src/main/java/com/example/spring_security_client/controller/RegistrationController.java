package com.example.spring_security_client.controller;

import com.example.spring_security_client.entity.User;
import com.example.spring_security_client.event.RegistrationEvent;
import com.example.spring_security_client.model.UserModel;
import com.example.spring_security_client.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @PostMapping("/newUser")
    public String registerNewUser(@RequestBody UserModel userModel) {

        User user = userService.createNewUser(userModel);

        applicationEventPublisher.publishEvent(new RegistrationEvent(user,"/url"));

        return "Success";
    }
}

