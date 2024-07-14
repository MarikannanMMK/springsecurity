package com.example.spring_security_client.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    private String firstName;
    private String lastName;
    private String emailId;
    private String role;
    private String password;
    private String matchingPassword;


}
