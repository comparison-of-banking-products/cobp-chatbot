package ru.cobp.support.service;

import ru.cobp.support.dto.SupportRequestDto;

public interface SupportService {

    void sendSupportRequest(SupportRequestDto dto);

}
