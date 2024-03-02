package ru.cobp.support.bot;

import ru.cobp.support.dto.SupportRequestDto;
import ru.cobp.support.dto.UserSupportDto;

public interface SupportChatbot {

    void notifyUserConnect(UserSupportDto dto);

    void sendSupportRequest(SupportRequestDto dto);

    void notifyUserDisconnect(UserSupportDto dto);

}
