package com.eco.beminichat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;


@Builder
public record MessageDto(
        String content,
        AccountConversationDto sender,
        Boolean isMine,
        Timestamp sentAt) {
}