package com.yassin.portfolio.service.impl;


import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import com.yassin.portfolio.model.ContactForm;

import com.yassin.portfolio.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Primary
@Slf4j
public class ResendEmailServiceImpl implements EmailService {


    public ResendEmailServiceImpl( ) {
    }

    @Value("${resend.api.key}")
    private String apiKey;

    @Value("${app.contact.email}")
    private String email;

    @Override
    @Async
    public void sendContactEmail(ContactForm contactForm) {
        log.info("Sending contact email from: {}", contactForm.getEmail());
        try {
            Resend resend = new Resend(apiKey);
            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from(contactForm.getEmail())
                    .to(email)
                    .subject(contactForm.getSubject())
                    .build();
            CreateEmailResponse data = resend.emails().send(params);
            log.info("Contact email sent successfully with Id: {}", data.getId());
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