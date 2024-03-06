package ru.cobp.support.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.cobp.support.dto.AgreementStatus;
import ru.cobp.support.dto.UserDto;
import ru.cobp.support.dto.UserSupportRequestDto;
import ru.cobp.support.exception.ExceptionUtil;

import java.util.List;
import java.util.StringJoiner;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatbotUtils {

    public static String buildUserSupportConnectText(UserDto dto) {
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add(buildUserSupportConnectHeaderMessage());
        sj.add(buildSupportRequestNameRow(dto.name()));
        sj.add(buildSupportRequestEmailRow(dto.email()));
        sj.add(buildSupportRequestAgreementRow(dto.agreementStatus()));
        return sj.toString();
    }

    public static String buildSupportRequestText(UserSupportRequestDto dto) {
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add(buildSupportRequestHeaderMessage());
        sj.add(buildSupportRequestNameRow(dto.name()));
        sj.add(buildSupportRequestEmailRow(dto.email()));
        sj.add(buildSupportRequestQuestionRow(dto.question()));
        return sj.toString();
    }

    public static String buildUserSupportDisconnectText(UserDto dto) {
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add(buildUserSupportDisconnectHeaderMessage());
        sj.add(buildSupportRequestNameRow(dto.name()));
        sj.add(buildSupportRequestEmailRow(dto.email()));
        return sj.toString();
    }

    public static String buildUserSupportConnectHeaderMessage() {
        return buildHeaderMessage("Connected to support chat +++");
    }

    public static String buildSupportRequestHeaderMessage() {
        return buildHeaderMessage("Message from support chat >>>");
    }

    private static String buildUserSupportDisconnectHeaderMessage() {
        return buildHeaderMessage("Disconnected from support chat ^^^");
    }

    public static String buildSupportRequestNameRow(String name) {
        return buildRowTitle("name") + name;
    }

    public static String buildSupportRequestEmailRow(String email) {
        return buildRowTitle("email") + email;
    }

    public static String buildSupportRequestAgreementRow(AgreementStatus status) {
        return buildRowTitle("agreement") + status;
    }

    public static String buildSupportRequestQuestionRow(String question) {
        return buildRowTitle("question") + question;
    }

    public static String buildHeaderMessage(String message) {
        return "<b><u><i>" + message + "</i></u></b>";
    }

    public static String buildRowTitle(String title) {
        return "     <b><i>" + title + "</i></b>:     ";
    }

    public static String parseSupportRequestReplyEmail(String text) {
        List<String> rows = text.lines().toList();
        if (rows.size() != 4) {
            throw ExceptionUtil.getSupportReplyEmailParseFailedException(text);
        }

        String emailRow = rows.get(2);
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
