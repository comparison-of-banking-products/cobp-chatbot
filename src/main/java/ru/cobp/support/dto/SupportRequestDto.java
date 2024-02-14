package ru.cobp.support.dto;

public record SupportRequestDto(String name, String email, AgreementStatus agreementStatus, String question) {
}
