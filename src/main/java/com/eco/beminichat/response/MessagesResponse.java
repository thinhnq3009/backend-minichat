package com.eco.beminichat.response;

import com.eco.beminichat.dto.MessageDto;
import com.eco.beminichat.response.base.ResponseEnable;

import java.util.ArrayList;
import java.util.Collection;

public class MessagesResponse extends ArrayList<MessageDto> implements ResponseEnable {
    public MessagesResponse(Collection<? extends MessageDto> c) {
        super(c);
    }
}
