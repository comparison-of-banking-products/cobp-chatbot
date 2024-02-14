package ru.cobp.support.exception;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBotsApiInstantiationFailedException extends RuntimeException {

    public TelegramBotsApiInstantiationFailedException(String message, TelegramApiException e) {
        super(message, e);
    }

}
