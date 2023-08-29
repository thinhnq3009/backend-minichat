package com.eco.beminichat.dto;

import lombok.Builder;

@Builder
public record AccountConversationDto(
        String username,
        String displayName,
        String avatarUrl) {

}
