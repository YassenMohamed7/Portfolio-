package com.yassin.portfolio.service;


import com.yassin.portfolio.model.ContactForm;
import org.springframework.stereotype.Service;

public interface EmailService {
    void sendContactEmail(ContactForm contactForm);
}