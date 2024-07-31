package com.akichou;

import com.akichou.config.MailProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.akichou")
@EnableJpaRepositories(basePackages = "com.akichou.repository")
@EntityScan(basePackages = "com.akichou.domain.entity")
@EnableScheduling
@EnableConfigurationProperties(MailProperties.class)
public class AkiChouBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(AkiChouBlogApplication.class, args) ;
    }
}
