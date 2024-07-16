package com.example.spring_security_client.controller;

import com.example.spring_security_client.entity.User;
import com.example.spring_security_client.entity.VerificationToken;
import com.example.spring_security_client.event.PasswordRestEvent;
import com.example.spring_security_client.event.RegistrationEvent;
import com.example.spring_security_client.event.ResentVerificationEvent;
import com.example.spring_security_client.model.PasswordModel;
import com.example.spring_security_client.model.UserModel;
import com.example.spring_security_client.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("/hello")
    public String helloSample() {
        return "Hello Users";
    }

    @PostMapping("/newUser")
    public String registerNewUser(@RequestBody UserModel userModel, final HttpServletRequest request) {

        User user = userService.createNewUser(userModel);

        applicationEventPublisher.publishEvent(new RegistrationEvent(user, applicationUrl(request)));

        return "Success";
    }

    private String applicationUrl(HttpServletRequest request) {

        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }


    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {

        //System.out.println("from URL " + token);

        String result = userService.ValidateVerficationToken(token);

        if (result.equalsIgnoreCase("valid")) {
            return "User verified successfully";
        }
        return "Bad User";

    }

    @GetMapping("/resentVerification")
    public String resentVerificationToken(@RequestParam("USER_ID") Long USER_ID, final HttpServletRequest request) {

        VerificationToken verificationToken = userService.resentVerificationtoken(USER_ID);
        applicationEventPublisher.publishEvent(new ResentVerificationEvent(verificationToken, applicationUrl(request)));

        return "Resent the VerificationMail";

    }

    @PostMapping("/passwordReset")
    public String passwordResetToken(@RequestBody PasswordModel passwordModel, final HttpServletRequest request) {

        User user = userService.passwordResetToken(passwordModel.getEmail());
        if (user != null) {
            applicationEventPublisher.publishEvent(new PasswordRestEvent(user, applicationUrl(request)));
        } else {
            return "Not a valid user";
        }

        return "Password Reset Mail sent";
    }

    @PostMapping("/savePassword")
    public String savepassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel) {

        String result = userService.ValidatePasswordResettoken(token);

        if (result.equalsIgnoreCase("valid")) {
            Optional<User> user = userService.findUserByPasswordResettoken(token);
            if (user.isPresent()) {
                userService.changePassword(user.get(), passwordModel.getNewPassword());
            }
        } else {
            return "Password Token Expried ";
        }
        return "Password Reset successfully";
    }

    @PostMapping("/changePassword")
    public  String changePassword(@RequestBody PasswordModel passwordModel){

        User user = userService.findUserByemail(passwordModel.getEmail());

        if(!(userService.verifyOldPassword(user.getPassword(),passwordModel.getOldPassword()))){
            return "Invalid Old Password";
        }
        userService.changePassword(user,passwordModel.getNewPassword());
        return "Password Changed Successfully";
    }
}

