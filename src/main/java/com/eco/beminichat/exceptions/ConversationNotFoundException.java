package com.eco.beminichat.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConversationNotFoundException extends RuntimeException{
    public ConversationNotFoundException(String message) {
        super(message);
        log.warn("Conversation not found exception have been init. Message <{}>",  message);
    }
}
