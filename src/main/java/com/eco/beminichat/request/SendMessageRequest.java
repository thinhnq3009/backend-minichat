package com.eco.beminichat.request;

import lombok.Data;

@Data
public class SendMessageRequest {
    private String message;
    private Long conversationId;

}
