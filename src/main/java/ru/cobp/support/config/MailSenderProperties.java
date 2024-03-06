package ru.cobp.support.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public record MailSenderProperties(

        @Value("${spring.mail.username}")
        String email

) {
}
