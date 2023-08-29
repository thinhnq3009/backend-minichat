package com.eco.beminichat.dto;

import com.eco.beminichat.response.base.ResponseEnable;
import lombok.Builder;

import java.sql.Timestamp;


@Builder
public record MessageDto(
        String content,
        AccountConversationDto sender,
        Boolean isMine,
        Timestamp sentAt) implements ResponseEnable {
}