package com.electrotienda.notification_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendReceipt(String to, Double amount){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("electrotienda@techecommerce.com");
            message.setTo(to);
            message.setSubject("Receipt for your purchase");
            message.setText("Thank you for your purchase! Your total amount was: $" + amount);

            mailSender.send(message);
            log.info("Email sent successfully to {}", to);
        }
        catch (Exception e){
            log.error("Error sending email to {}", to, e);
        }
    }
}
