package com.example.spring_security_client.service;

import com.example.spring_security_client.entity.PasswordResetToken;
import com.example.spring_security_client.entity.User;
import com.example.spring_security_client.entity.VerificationToken;
import com.example.spring_security_client.model.UserModel;
import com.example.spring_security_client.repository.PasswordResetTokenRepository;
import com.example.spring_security_client.repository.UserRepository;
import com.example.spring_security_client.repository.VerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public User createNewUser(UserModel userModel) {

        User user = new User();

        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user.setEmailId(userModel.getEmailId());
        user.setRole("USER");

        userRepository.save(user);

        return user;
    }

    @Override
    public void SaveVerificationTokenWithUser(User user, String token) {

        VerificationToken verificationToken = new VerificationToken(user, token);

        verificationRepository.save(verificationToken);
    }

    @Override
    public String ValidateVerficationToken(String token) {

        VerificationToken verificationToken = verificationRepository.findByToken(token);
        if (verificationToken == null) {
            return "Invalid";
        }

        Calendar cal = Calendar.getInstance();

        if (verificationToken.getExpirationDate().getTime() - cal.getTime().getTime() <= 0) {
            verificationRepository.delete(verificationToken);
            return "Token got exprired";
        }

        User user = verificationToken.getUser();
        user.setEnable(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public VerificationToken resentVerificationtoken(Long user_id) {
        VerificationToken verificationToken = verificationRepository.findByUser_id(user_id);
        return verificationToken;
    }

    @Override
    public User passwordResetToken(String email_id) {

        User user = userRepository.findByEmailId(email_id);

        return user;
    }

    @Override
    public void savePasswordResetToken(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user,token);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String ValidatePasswordResettoken(String token) {

       PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null) {
            return "Invalid";
        }

        Calendar cal = Calendar.getInstance();

        if (passwordResetToken.getExpirationDate().getTime() - cal.getTime().getTime() <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return "Token got exprired";
        }

        return "valid";
    }

    @Override
    public Optional<User> findUserByPasswordResettoken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public User findUserByemail(String email) {

        User user = userRepository.findByEmailId(email);

        return user;
    }

    @Override
    public boolean verifyOldPassword(String password, String oldPassword) {
        return passwordEncoder.matches(oldPassword,password);
    }
}
