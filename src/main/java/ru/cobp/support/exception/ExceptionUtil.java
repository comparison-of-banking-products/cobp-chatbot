package ru.cobp.support.exception;

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

    public static TelegramBotSendMessageFailedException getTelegramBotSendMessageFailedException(
            TelegramApiException e
    ) {
        return new TelegramBotSendMessageFailedException(
                ExceptionMessage.TELEGRAM_BOT_SEND_MESSAGE_FAILED, e
        );
    }

    public static SupportReplyEmailParseFailedException getSupportReplyEmailParseFailedException(String text) {
        return new SupportReplyEmailParseFailedException(
                String.format("%s, [%s]", ExceptionMessage.SUPPORT_REPLY_EMAIL_PARSE_FAILED, text)
        );
    }

}
