package com.example.spring_security_client.event;

import com.example.spring_security_client.entity.VerificationToken;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class ResentVerificationEvent extends ApplicationEvent {

    private VerificationToken verificationToken;
    private String applicationUrl;


    public ResentVerificationEvent(VerificationToken verificationToken, String applicationURL) {
        super(verificationToken);
        this.verificationToken = verificationToken;
        this.applicationUrl = applicationURL;
    }
}
