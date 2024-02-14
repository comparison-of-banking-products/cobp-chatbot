package ru.cobp.support.bot;

import ru.cobp.support.dto.SupportRequestDto;

public interface SupportChatbot {

    void sendSupportRequest(SupportRequestDto dto);

}
