package com.example.spring_security_client.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class VerificationToken {

    private static final int expirationTime = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String token;

    private Date expirationDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))
    private User user;


    public VerificationToken(User user, String token) {
        super();
        this.user = user;
        this.token = token;
        this.expirationDate = CalculateExpirationTime(expirationTime);
    }

    public VerificationToken(String token) {
        super();
        this.token = token;
        this.expirationDate = CalculateExpirationTime(expirationTime);
    }

    private Date CalculateExpirationTime(int expirationDate) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());

    }

}
