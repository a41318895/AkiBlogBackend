package com.akichou.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Mail Properties
 *
 * @author Aki Chou
 * @date 2024/07/28 Sun.
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties {

    private String host;

    private int port;

    private String username;

    private String password;

    private final Properties properties = new Properties();

}
