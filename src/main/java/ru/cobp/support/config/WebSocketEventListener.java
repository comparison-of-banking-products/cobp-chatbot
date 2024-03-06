package ru.cobp.support.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import ru.cobp.support.common.CommonConstants;
import ru.cobp.support.dto.UserDto;
import ru.cobp.support.service.SupportService;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SupportService supportService;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        var sessionAttributes = headerAccessor.getSessionAttributes();
        if (sessionAttributes != null) {
            String user = (String) sessionAttributes.get(CommonConstants.NAME);
            String email = (String) sessionAttributes.get(CommonConstants.EMAIL);
            supportService.notifyAboutUserDisconnection(new UserDto(user, email, null));
        }
    }

}
