package ru.cobp.support.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.cobp.support.config.MailSenderProperties;
import ru.cobp.support.exception.ExceptionUtil;

@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailService {

    private final MailSenderProperties mailSenderProperties;

    private final JavaMailSender mailSender;

    @Override
    public void sendSimpleMailMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(getFromEmail());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        send(message);
    }

    private String getFromEmail() {
        return mailSenderProperties.email();
    }

    private void send(SimpleMailMessage message) {
        if (message == null) {
            return;
        }

        try {
            mailSender.send(message);
        } catch (MailException e) {
            throw ExceptionUtil.getSendMailMessageFailedException(e);
        }
    }

}
