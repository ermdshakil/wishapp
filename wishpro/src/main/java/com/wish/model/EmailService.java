package com.wish.model;

import com.wish.WishproApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @SuppressWarnings("unused")
	private final WishproApplication wishproApplication;

    @Autowired
    private JavaMailSender mailSender;

    EmailService(WishproApplication wishproApplication) {
        this.wishproApplication = wishproApplication;
    }

    public void sendWish(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}