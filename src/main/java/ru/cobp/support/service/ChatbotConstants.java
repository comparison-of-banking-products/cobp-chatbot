package ru.cobp.support.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatbotConstants {

    public static final String HELP_COMMAND = "/help";

    public static final String SEND_MAIL_COMMAND = "/send_mail";

    public static final String COMMAND_NOT_FOUND_FORMAT = "Bot command not found, use %s";

    public static final String HELP_MESSAGE_FORMAT = """
            %s - display help message
                                            
            %s - to send simple mail write command and info below:
            ${email}
            ${subject}
            ${text}
            """;

}
