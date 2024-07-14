package com.example.spring_security_client.service;


import com.example.spring_security_client.entity.User;
import com.example.spring_security_client.model.UserModel;

public interface UserService {

    public User createNewUser(UserModel userModel);
}
