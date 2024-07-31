package com.akichou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration;

@SpringBootApplication(exclude = {MailSenderAutoConfiguration.class, MailSenderValidatorAutoConfiguration.class})
public class AkiChouBlogAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AkiChouBlogAdminApplication.class, args);
    }
}
