package ru.cobp.chatbot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.cobp.chatbot.config.TelegramBotProperty;
import ru.cobp.chatbot.exception.ExceptionUtil;

@Component
public class Chatbot extends TelegramLongPollingBot {

    private final TelegramBotProperty botProperty;

    public Chatbot(TelegramBotsApi botsApi, TelegramBotProperty botProperty) {
        super(botProperty.getToken());
        this.botProperty = botProperty;
        try {
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            throw ExceptionUtil.getTelegramBotRegistrationFailedException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        System.out.println(message.getText());
    }

    @Override
    public String getBotUsername() {
        return botProperty.getUsername();
    }

}
