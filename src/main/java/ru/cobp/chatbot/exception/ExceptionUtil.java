package ru.cobp.chatbot.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionUtil {

    public static TelegramBotRegistrationFailedException getTelegramBotRegistrationFailedException(
            TelegramApiException e
    ) {
        return new TelegramBotRegistrationFailedException(
                ExceptionMessage.TELEGRAM_BOT_REGISTRATION_FAILED, e
        );
    }

    public static TelegramBotsApiInstantiationFailedException getTelegramBotsApiInstantiationFailedException(
            TelegramApiException e
    ) {
        return new TelegramBotsApiInstantiationFailedException(
                ExceptionMessage.TELEGRAM_BOTS_API_INSTANTIATION_FAILED, e
        );
    }

}
