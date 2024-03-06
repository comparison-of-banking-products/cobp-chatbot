package ru.cobp.support.service;

import ru.cobp.support.dto.UserDto;
import ru.cobp.support.dto.UserSupportRequestDto;

public interface SupportService {

    void notifyAboutUserConnection(UserDto dto);

    void sendSupportRequest(UserSupportRequestDto dto);

    void notifyAboutUserDisconnection(UserDto dto);

    void sendSimp(String destination, String text);

    void sendMail(String email, String subject, String text);

}
