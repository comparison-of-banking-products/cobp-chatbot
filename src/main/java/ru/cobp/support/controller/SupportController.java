package ru.cobp.support.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.cobp.support.common.WebsocketConstants;
import ru.cobp.support.dto.SupportRequestDto;
import ru.cobp.support.service.SupportService;

@Controller
@RequiredArgsConstructor
public class SupportController {

    private final SupportService supportService;

    @MessageMapping(WebsocketConstants.SUPPORT_SUFFIX)
    @SendTo(WebsocketConstants.SUPPORT_DESTINATION)
    public void sendSupportRequest(@Payload SupportRequestDto dto) {
        supportService.sendSupportRequest(dto);
    }

}
