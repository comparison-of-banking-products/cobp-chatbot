package ru.cobp.support.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.cobp.support.common.CommonConstants;
import ru.cobp.support.config.TelegramBotProperties;
import ru.cobp.support.dto.UserDto;
import ru.cobp.support.dto.UserSupportRequestDto;
import ru.cobp.support.exception.ExceptionUtil;

import java.util.List;
import java.util.stream.Collectors;

import static ru.cobp.support.service.ChatbotConstants.COMMAND_NOT_FOUND_FORMAT;
import static ru.cobp.support.service.ChatbotConstants.HELP_COMMAND;
import static ru.cobp.support.service.ChatbotConstants.HELP_MESSAGE_FORMAT;
import static ru.cobp.support.service.ChatbotConstants.SEND_MAIL_COMMAND;

@Component
public class ChatbotServiceImpl extends TelegramLongPollingBot implements ChatbotService {

    private final TelegramBotProperties botProperties;

    private final SupportService supportService;

    @Autowired
    public ChatbotServiceImpl(
            TelegramBotsApi botsApi,
            TelegramBotProperties botProperties,
            SupportService supportService
    ) {
        super(botProperties.token());
        this.botProperties = botProperties;
        this.supportService = supportService;

        try {
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            throw ExceptionUtil.getTelegramBotRegistrationFailedException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String messageText = message.getText();

        if (isBotCommand(messageText)) {
            handleBotCommand(messageText);

        } else {
            Message reply = message.getReplyToMessage();
            if (reply != null) {
                String replyText = reply.getText();
                String replyEmail = ChatbotUtils.parseSupportRequestReplyEmail(replyText);
                sendSupportSimpResponse(replyEmail, messageText);
            }
        }
    }

    private boolean isBotCommand(String s) {
        return s.startsWith("/");
    }

    private void handleBotCommand(String commandText) {
        if (commandText.startsWith(HELP_COMMAND)) {
            handleHelpCommand();

        } else if (commandText.startsWith(SEND_MAIL_COMMAND)) {
            handleSendMailCommand(commandText);

        } else {
            handleCommandNotFound();
        }
    }

    private void handleHelpCommand() {
        String helpText = String.format(HELP_MESSAGE_FORMAT, HELP_COMMAND, SEND_MAIL_COMMAND);
        send(helpText);
    }

    private void handleSendMailCommand(String commandText) {
        MailRecord mailRecord = parseSendMailCommandText(commandText);
        supportService.sendMail(mailRecord.email(), mailRecord.subject(), mailRecord.text());
    }

    private MailRecord parseSendMailCommandText(String commandText) {
        List<String> lines = commandText.lines()
                .toList();

        String email = lines.get(1);
        String subject = lines.get(2);
        String text = lines.subList(3, lines.size())
                .stream()
                .map(String::toString)
                .collect(Collectors
                        .joining(System.lineSeparator()));

        return new MailRecord(email, subject, text);
    }

    private void handleCommandNotFound() {
        String text = String.format(COMMAND_NOT_FOUND_FORMAT, HELP_COMMAND);
        send(text);
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

    @Override
    public void notifyAboutUserConnection(UserDto dto) {
        String text = ChatbotUtils.buildUserSupportConnectText(dto);
        send(text);
    }

    @Override
    public void sendSupportRequest(UserSupportRequestDto dto) {
        String text = ChatbotUtils.buildSupportRequestText(dto);
        send(text);
    }

    @Override
    public void notifyAboutUserDisconnection(UserDto dto) {
        String text = ChatbotUtils.buildUserSupportDisconnectText(dto);
        send(text);
    }

    private void send(String text) {
        SendMessage message = new SendMessage();
        message.setChatId(getChatId());
        message.setMessageThreadId(getThreadId());
        message.setParseMode(CommonConstants.TELEGRAM_MESSAGE_PARSE_MODE);
        message.setText(text);
        send(message);
    }

    private void send(SendMessage message) {
        if (message == null) {
            return;
        }

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw ExceptionUtil.getTelegramBotSendMessageFailedException(e);
        }
    }

    private void sendSupportSimpResponse(String destination, String messageText) {
        supportService.sendSimp(destination, messageText);
    }

    private record MailRecord(String email, String subject, String text) {
    }

}
