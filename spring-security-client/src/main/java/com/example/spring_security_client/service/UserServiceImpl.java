package com.example.spring_security_client.service;

import com.example.spring_security_client.entity.User;
import com.example.spring_security_client.model.UserModel;
import com.example.spring_security_client.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createNewUser(UserModel userModel) {

        User user = new User();

        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user.setEmailId(userModel.getEmailId());
        user.setRole(userModel.getRole());

        userRepository.save(user);

        return user;
    }
}
