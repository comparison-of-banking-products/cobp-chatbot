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
                String.format(ExceptionMessage.TELEGRAM_BOT_REGISTRATION_FAILED), e
        );
    }

    public static TelegramBotsApiInstantiationFailedException getTelegramBotsApiInstantiationFailedException(
            TelegramApiException e
    ) {
        return new TelegramBotsApiInstantiationFailedException(
                String.format(ExceptionMessage.TELEGRAM_BOTS_API_INSTANTIATION_FAILED), e
        );
    }

}
