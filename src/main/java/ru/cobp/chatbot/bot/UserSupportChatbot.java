package ru.cobp.chatbot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.cobp.chatbot.config.TelegramBotProperties;
import ru.cobp.chatbot.exception.ExceptionUtil;

@Component
public class UserSupportChatbot extends TelegramLongPollingBot {

    private final TelegramBotProperties botProperties;

    @Autowired
    public UserSupportChatbot(TelegramBotsApi botsApi, TelegramBotProperties botProperties) {
        super(botProperties.getToken());
        this.botProperties = botProperties;
        try {
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            throw ExceptionUtil.getTelegramBotRegistrationFailedException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
//        Message message = update.getMessage();
//        System.out.println(message.getText());
//
//        var resp = new SendMessage();
//        resp.setChatId(message.getChatId());
//        resp.setText("Iam listening");
//        sendAnswer(resp);
    }

    @Override
    public String getBotUsername() {
        return botProperties.getUsername();
    }

//    public void sendAnswer(SendMessage sendMessage) {
//        if (sendMessage == null) {
//            return;
//        }
//
//        try {
//            execute(sendMessage);
//        } catch (TelegramApiException e) {
//            throw new RuntimeException(e); //TODO -- add custom exception
//        }
//    }

//    public void sendAds() {
//        SendMessage ad = new SendMessage;
//        ad.setText("test");
//        execute(ad);
//    }

}
