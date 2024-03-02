package ru.cobp.support.service;

import ru.cobp.support.dto.SupportRequestDto;
import ru.cobp.support.dto.UserSupportDto;

public interface SupportService {

    void notifyUserConnect(UserSupportDto dto);

    void sendSupportRequest(SupportRequestDto dto);

    void notifyUserDisconnect(UserSupportDto dto);

}
