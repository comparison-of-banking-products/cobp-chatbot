package ru.cobp.support.service;

public interface MailService {

    void sendSimpleMailMessage(String to, String subject, String text);

}
