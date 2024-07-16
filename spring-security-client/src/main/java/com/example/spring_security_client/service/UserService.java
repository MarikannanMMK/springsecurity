package com.example.spring_security_client.service;


import com.example.spring_security_client.entity.User;
import com.example.spring_security_client.entity.VerificationToken;
import com.example.spring_security_client.model.UserModel;

import java.util.Optional;

public interface UserService {

    public User createNewUser(UserModel userModel);

    void SaveVerificationTokenWithUser(User user, String token);

    String ValidateVerficationToken(String token);

    VerificationToken resentVerificationtoken(Long user_id);

    User passwordResetToken(String email_id);

    void savePasswordResetToken(User user, String token);

    String ValidatePasswordResettoken(String token);


    Optional<User> findUserByPasswordResettoken(String token);

    void changePassword(User user, String newPassword);

    User findUserByemail(String email);

    boolean verifyOldPassword(String password, String oldPassword);
}
