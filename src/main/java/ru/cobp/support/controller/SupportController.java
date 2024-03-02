package ru.cobp.support.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import ru.cobp.support.common.CommonConstants;
import ru.cobp.support.common.WebSocketConstants;
import ru.cobp.support.dto.SupportRequestDto;
import ru.cobp.support.dto.UserSupportDto;
import ru.cobp.support.service.SupportService;

@Controller
@RequiredArgsConstructor
public class SupportController {

    private final SupportService supportService;

    @MessageMapping(WebSocketConstants.SUPPORT_CONNECT_DESTINATION)
    public void handleUserSupportConnect(
            @Payload UserSupportDto dto,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        var sessionAttributes = headerAccessor.getSessionAttributes();
        if (sessionAttributes != null) {
            sessionAttributes.put(CommonConstants.NAME, dto.name());
            sessionAttributes.put(CommonConstants.EMAIL, dto.email());
        }

        supportService.notifyUserConnect(dto);
    }

    @MessageMapping(WebSocketConstants.SUPPORT_DESTINATION)
    public void sendSupportRequest(
            @Payload SupportRequestDto dto
    ) {
        supportService.sendSupportRequest(dto);
    }

}
