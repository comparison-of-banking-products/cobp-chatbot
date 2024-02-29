package ru.cobp.support.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.cobp.support.common.WebsocketConstants;
import ru.cobp.support.dto.SupportRequestDto;
import ru.cobp.support.service.SupportService;

@Controller
@RequiredArgsConstructor
public class SupportController {

    private final SimpMessagingTemplate messagingTemplate;

    private final SupportService supportService;

    @MessageMapping(WebsocketConstants.SUPPORT_DESTINATION)
    public void sendSupportRequest(@Payload SupportRequestDto dto) {
        messagingTemplate.convertAndSend(
                WebsocketConstants.QUEUE_SUPPORT_DESTINATION + "/" + dto.email(),
                "Welcome to support chat! Ask your question and our specialist will answer it shortly"
        );
        supportService.sendSupportRequest(dto);
    }

}
