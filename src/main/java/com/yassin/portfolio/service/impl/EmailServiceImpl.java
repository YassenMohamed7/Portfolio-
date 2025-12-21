package com.yassin.portfolio.service.impl;


import com.yassin.portfolio.model.ContactForm;

import com.yassin.portfolio.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${app.contact.email}")
    private String contactEmail;

    @Override
    @Async
    public void sendContactEmail(ContactForm contactForm) {
        log.info("Sending contact email from: {}", contactForm.getEmail());

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(contactEmail);
            message.setSubject("Portfolio Contact: " +
                    (contactForm.getSubject() != null ? contactForm.getSubject() : "New Message"));
            message.setText(buildEmailBody(contactForm));
            message.setReplyTo(contactForm.getEmail());

            mailSender.send(message);
            log.info("Contact email sent successfully");
        } catch (Exception e) {
            log.error("Failed to send contact email", e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private String buildEmailBody(ContactForm form) {
        return String.format("""
            New contact form submission:
            
            Name: %s
            Email: %s
            Subject: %s
            
            Message:
            %s
            """,
                form.getName(),
                form.getEmail(),
                form.getSubject() != null ? form.getSubject() : "N/A",
                form.getMessage()
        );
    }
}