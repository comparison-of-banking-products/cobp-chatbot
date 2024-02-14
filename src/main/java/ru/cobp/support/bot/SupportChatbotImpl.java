package ru.cobp.support.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.cobp.support.common.WebsocketConstants;
import ru.cobp.support.config.TelegramBotProperties;
import ru.cobp.support.dto.SupportRequestDto;
import ru.cobp.support.exception.ExceptionUtil;

import java.util.StringJoiner;

@Component
public class SupportChatbotImpl extends TelegramLongPollingBot implements SupportChatbot {

    private final TelegramBotProperties botProperties;

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public SupportChatbotImpl(
            TelegramBotsApi botsApi,
            TelegramBotProperties botProperties,
            SimpMessagingTemplate messagingTemplate
    ) {
        super(botProperties.token());
        this.botProperties = botProperties;
        this.messagingTemplate = messagingTemplate;

        try {
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            throw ExceptionUtil.getTelegramBotRegistrationFailedException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        this.sendSupportResponse(update.getMessage().getText());
    }

    private void sendSupportResponse(String message) {
        messagingTemplate.convertAndSend(WebsocketConstants.SUPPORT_DESTINATION, message);
    }

    @Override
    public String getBotUsername() {
        return botProperties.username();
    }

    private String getSupportGroupId() {
        return botProperties.groupId();
    }

    private Integer getSupportTopicId() {
        return botProperties.topicId();
    }

    private void sendMessage(SendMessage message) {
        if (message == null) {
            return;
        }

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw ExceptionUtil.getTelegramBotSendMessageFailedException(e);
        }
    }

    @Override
    public void sendSupportRequest(SupportRequestDto dto) {
        SendMessage message = new SendMessage();
        message.setChatId(this.getSupportGroupId());
        message.setText(this.buildSupportRequestText(dto));
        message.setMessageThreadId(this.getSupportTopicId());
        this.sendMessage(message);
    }

    private String buildSupportRequestText(SupportRequestDto dto) {
        StringJoiner sj = new StringJoiner("\n");
        sj.add(dto.name());
        sj.add(dto.email());
        sj.add(dto.agreementStatus().toString());
        sj.add(dto.question());
        return sj.toString();
    }

}
