package ru.cobp.support.exception;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBotRegistrationFailedException extends RuntimeException {

    public TelegramBotRegistrationFailedException(String message, TelegramApiException e) {
        super(message, e);
    }

}
