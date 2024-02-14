package ru.cobp.support.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cobp.support.bot.SupportChatbot;
import ru.cobp.support.dto.SupportRequestDto;

@Service
@RequiredArgsConstructor
public class SupportServiceImpl implements SupportService {

    private final SupportChatbot supportChatbot;

    @Override
    public void sendSupportRequest(SupportRequestDto dto) {
        supportChatbot.sendSupportRequest(dto);
    }

}
