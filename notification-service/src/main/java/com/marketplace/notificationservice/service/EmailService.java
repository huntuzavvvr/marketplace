package com.marketplace.notificationservice.service;

public interface EmailService  {
    void sendEmail(String to, String subject, String content);
}
