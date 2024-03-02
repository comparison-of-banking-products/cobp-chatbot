package ru.cobp.support.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cobp.support.bot.SupportChatbot;
import ru.cobp.support.dto.SupportRequestDto;
import ru.cobp.support.dto.UserSupportDto;

@Service
@RequiredArgsConstructor
public class SupportServiceImpl implements SupportService {

    private final SupportChatbot supportChatbot;

    @Override
    public void notifyUserConnect(UserSupportDto dto) {
        supportChatbot.notifyUserConnect(dto);
    }

    @Override
    public void sendSupportRequest(SupportRequestDto dto) {
        supportChatbot.sendSupportRequest(dto);
    }

    @Override
    public void notifyUserDisconnect(UserSupportDto dto) {
        supportChatbot.notifyUserDisconnect(dto);
    }

}
