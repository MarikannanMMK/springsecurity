package com.example.spring_security_client.repository;

import com.example.spring_security_client.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends JpaRepository<VerificationToken,Long> {

    VerificationToken findByToken(String token);


    VerificationToken findByUser_id(Long user_id);
}
