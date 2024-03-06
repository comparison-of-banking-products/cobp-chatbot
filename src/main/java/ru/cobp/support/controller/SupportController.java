package ru.cobp.support.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import ru.cobp.support.common.CommonConstants;
import ru.cobp.support.common.WebSocketConstants;
import ru.cobp.support.dto.UserSupportRequestDto;
import ru.cobp.support.dto.UserDto;
import ru.cobp.support.service.SupportService;

@Controller
@RequiredArgsConstructor
public class SupportController {

    private final SupportService supportService;

    @MessageMapping(WebSocketConstants.SUPPORT_CONNECT_DESTINATION)
    public void handleUserConnectionToSupport(
            @Payload UserDto dto,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        var sessionAttributes = headerAccessor.getSessionAttributes();
        if (sessionAttributes != null) {
            sessionAttributes.put(CommonConstants.NAME, dto.name());
            sessionAttributes.put(CommonConstants.EMAIL, dto.email());
        }

        supportService.notifyAboutUserConnection(dto);
    }

    @MessageMapping(WebSocketConstants.SUPPORT_DESTINATION)
    public void handleUserSupportRequest(
            @Payload UserSupportRequestDto dto
    ) {
        supportService.sendSupportRequest(dto);
    }

}
