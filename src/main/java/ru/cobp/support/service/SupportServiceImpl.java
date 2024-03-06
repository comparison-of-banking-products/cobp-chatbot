package ru.cobp.support.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.cobp.support.common.CommonMessage;
import ru.cobp.support.common.WebSocketConstants;
import ru.cobp.support.dto.UserDto;
import ru.cobp.support.dto.UserSupportRequestDto;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class SupportServiceImpl implements SupportService {

    @Lazy
    private final ChatbotService chatbotService;

    private final SimpMessagingTemplate messagingTemplate;

    private final MailService mailService;

    @Override
    public void notifyAboutUserConnection(UserDto dto) {
        String destination = buildQueueSupportDestination(dto.email());
        messagingTemplate.convertAndSend(destination, CommonMessage.WELCOME_MESSAGE_FROM_SUPPORT);
        chatbotService.notifyAboutUserConnection(dto);
    }

    @Override
    public void sendSupportRequest(UserSupportRequestDto dto) {
        chatbotService.sendSupportRequest(dto);
    }

    @Override
    public void notifyAboutUserDisconnection(UserDto dto) {
        chatbotService.notifyAboutUserDisconnection(dto);
    }

    @Override
    public void sendSimp(String destination, String text) {
        messagingTemplate.convertAndSend(buildQueueSupportDestination(destination), text);
    }

    @Override
    public void sendMail(String email, String subject, String text) {
        mailService.sendSimpleMailMessage(email, subject, text);
    }

    private String buildQueueSupportDestination(String email) {
        return WebSocketConstants.QUEUE_SUPPORT_DESTINATION + "/" + email;
    }

}
