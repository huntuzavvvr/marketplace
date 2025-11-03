package com.marketplace.authservice.service;

import com.marketplace.authservice.dto.LoginDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class VerificationServiceImpl {
    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationCode(String email, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Verification Code");
        message.setText(verificationCode);
        mailSender.send(message);
    }
}
