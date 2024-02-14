package ru.cobp.support.exception;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBotSendMessageFailedException extends RuntimeException {

    public TelegramBotSendMessageFailedException(String message, TelegramApiException e) {
        super(message, e);
    }

}
