package ru.cobp.support.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public record TelegramBotProperties(

        @Value("${telegram.bot.username}")
        String username,

        @Value("${telegram.bot.token}")
        String token,

        @Value("${telegram.bot.group.id}")
        String groupId,

        @Value("${telegram.bot.topic.id}")
        Integer topicId

) {
}
