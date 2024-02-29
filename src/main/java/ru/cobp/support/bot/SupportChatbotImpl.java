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
import ru.cobp.support.common.WebsocketConstants;
import ru.cobp.support.config.TelegramBotProperties;
import ru.cobp.support.dto.AgreementStatus;
import ru.cobp.support.dto.SupportRequestDto;
import ru.cobp.support.exception.ExceptionUtil;

import java.util.List;
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
        Message message = update.getMessage();
        if (message.getReplyToMessage() != null) {
            String replyEmail = parseSupportRequestReplyEmail(message.getReplyToMessage().getText());
            this.sendSupportResponse(replyEmail, message.getText());
        }
    }

    private void sendSupportResponse(String email, String message) {
        String destination = WebsocketConstants.QUEUE_SUPPORT_DESTINATION + "/" + email;
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
    public void sendSupportRequest(SupportRequestDto dto) {
        SendMessage message = new SendMessage();
        message.setChatId(this.getChatId());
        message.setMessageThreadId(this.getThreadId());
        message.setText(this.buildSupportRequestText(dto));
        message.setParseMode("html");
        this.sendMessage(message);
    }

    private String buildSupportRequestText(SupportRequestDto dto) {
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add(buildSupportRequestHeaderMessage());
        sj.add("");
        sj.add(buildSupportRequestNameRow(dto.name()));
        sj.add(buildSupportRequestEmailRow(dto.email()));
        sj.add(buildSupportRequestAgreementRow(dto.agreementStatus()));
        sj.add(buildSupportRequestQuestionRow(dto.question()));
        return sj.toString();
    }

    private String buildSupportRequestHeaderMessage() {
        return buildHeaderMessage("Message from support chat");
    }

    private String buildSupportRequestNameRow(String name) {
        return buildRowTitle("name") + name;
    }

    private String buildSupportRequestEmailRow(String email) {
        return buildRowTitle("email") + email;
    }

    private String buildSupportRequestAgreementRow(AgreementStatus status) {
        return buildRowTitle("agreement") + status;
    }

    private String buildSupportRequestQuestionRow(String question) {
        return buildRowTitle("question") + question;
    }

    private String buildHeaderMessage(String message) {
        return "<b><u><i>" + message + "</i></u></b>";
    }

    private String buildRowTitle(String title) {
        return "  <b><i>" + title + "</i></b>:  ";
    }

    private String parseSupportRequestReplyEmail(String text) {
        List<String> rows = text.lines().toList();
        if (rows.size() != 6) {
            throw ExceptionUtil.getSupportReplyEmailParseFailedException(text);
        }

        String emailRow = rows.get(3);
        if (!emailRow.contains("email")) {
            throw ExceptionUtil.getSupportReplyEmailParseFailedException(emailRow);
        }

        String email = emailRow.split(":")[1].trim();
        if (email.isBlank() || !email.contains("@")) {
            throw ExceptionUtil.getSupportReplyEmailParseFailedException(email);
        }

        return email;
    }

}
