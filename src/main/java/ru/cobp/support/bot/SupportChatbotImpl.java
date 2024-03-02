package ru.cobp.support.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.cobp.support.common.WebSocketConstants;
import ru.cobp.support.config.TelegramBotProperties;
import ru.cobp.support.dto.SupportRequestDto;
import ru.cobp.support.dto.UserSupportDto;
import ru.cobp.support.exception.ExceptionUtil;


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
        Message message = update.getMessage();
        if (message.getReplyToMessage() != null) {
            String text = message.getReplyToMessage().getText();
            String replyEmail = ChatbotUtils.parseSupportRequestReplyEmail(text);
            this.sendSupportResponse(replyEmail, message.getText());
        }
    }

    private void sendSupportResponse(String email, String message) {
        String destination = WebSocketConstants.QUEUE_SUPPORT_DESTINATION + "/" + email;
        messagingTemplate.convertAndSend(destination, message);
    }

    @Override
    public String getBotUsername() {
        return botProperties.username();
    }

    private Long getChatId() {
        return botProperties.chatId();
    }

    private Integer getThreadId() {
        return botProperties.threadId();
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
    public void notifyUserConnect(UserSupportDto dto) {
        messagingTemplate.convertAndSend(
                WebSocketConstants.QUEUE_SUPPORT_DESTINATION + "/" + dto.email(),
                "Welcome to support chat! Ask your question and our specialist will answer it shortly"
        );

        SendMessage message = this.buildMessageSkeleton();
        message.setText(ChatbotUtils.buildUserSupportConnectText(dto));
        this.sendMessage(message);
    }

    @Override
    public void sendSupportRequest(SupportRequestDto dto) {
        SendMessage message = this.buildMessageSkeleton();
        message.setText(ChatbotUtils.buildSupportRequestText(dto));
        this.sendMessage(message);
    }

    @Override
    public void notifyUserDisconnect(UserSupportDto dto) {
        SendMessage message = this.buildMessageSkeleton();
        message.setText(ChatbotUtils.buildUserSupportDisconnectText(dto));
        this.sendMessage(message);
    }

    private SendMessage buildMessageSkeleton() {
        SendMessage message = new SendMessage();
        message.setChatId(this.getChatId());
        message.setMessageThreadId(this.getThreadId());
        message.setParseMode("html");
        return message;
    }

}
