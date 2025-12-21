package com.yassin.portfolio.controller;


import com.yassin.portfolio.model.ContactForm;
import com.yassin.portfolio.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/contact")
@Slf4j
public class ContactController {

    private final EmailService emailService;

    public ContactController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping
    public String showContactForm(Model model) {
        model.addAttribute("contactForm", new ContactForm());
        return "contact";
    }

    @PostMapping
    public String submitContactForm(@Valid @ModelAttribute ContactForm contactForm,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.warn("Contact form validation failed: {}", bindingResult.getAllErrors());
            return "contact";
        }

        try {
            emailService.sendContactEmail(contactForm);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Thank you for your message! I'll get back to you soon.");
            log.info("Contact form submitted successfully from: {}", contactForm.getEmail());
        } catch (Exception e) {
            log.error("Failed to process contact form", e);
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Sorry, there was an error sending your message. Please try again later.");
        }

        return "redirect:/contact";
    }
}