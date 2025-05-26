package com.kunal.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    void sendEmail() {
        emailService.sendEmail(
                "eleventha021104@gmail.com",
                "Testing java mail sender",
                "Hi just a test"
        );
    }
}
