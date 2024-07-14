package com.example.spring_security_client.event;


import com.example.spring_security_client.entity.User;
import org.springframework.context.ApplicationEvent;

public class RegistrationEvent extends ApplicationEvent {

    private  User user;
    private String applicationUrl;

    public RegistrationEvent(User user , String applicationUrl) {
        super(user);
        this.user  = user;
        this.applicationUrl = applicationUrl;

    }




}
