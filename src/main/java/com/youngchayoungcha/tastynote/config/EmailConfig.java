package com.youngchayoungcha.tastynote.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:application-mail.properties")
public class EmailConfig {
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.smtp.auth}")
    private String auth;
    @Value("${spring.mail.smtp.starttls.enable}")
    private String starttlsEnable;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setPort(port);
        mailSenderImpl.setHost(host);
        mailSenderImpl.setUsername(username);
        mailSenderImpl.setPassword(password);
        mailSenderImpl.setDefaultEncoding("UTF-8");
        mailSenderImpl.setJavaMailProperties(getMailProperties());
        return mailSenderImpl;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", auth);
        properties.put("mail.smtp.starttls.enable", starttlsEnable);
//        properties.put("mail.transport.protocol", "smtp");
//        properties.put("mail.debug", "true");
        return properties;
    }
}
