package ru.cobp.support.service;

import ru.cobp.support.dto.UserSupportRequestDto;
import ru.cobp.support.dto.UserDto;

public interface ChatbotService {

    void notifyAboutUserConnection(UserDto dto);

    void sendSupportRequest(UserSupportRequestDto dto);

    void notifyAboutUserDisconnection(UserDto dto);

}
