package com.eco.beminichat.response;

import com.eco.beminichat.dto.MessageDto;
import com.eco.beminichat.enitities.Message;

import java.util.ArrayList;
import java.util.Collection;

public class MessagesResponse extends ArrayList<MessageDto> {
    public MessagesResponse(Collection<? extends MessageDto> c) {
        super(c);
    }
}
