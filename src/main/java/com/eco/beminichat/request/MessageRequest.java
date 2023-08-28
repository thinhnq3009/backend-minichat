package com.eco.beminichat.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MessageRequest {
    String content;
    Long conversationId;
}
