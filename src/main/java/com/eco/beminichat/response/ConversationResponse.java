package com.eco.beminichat.response;

import com.eco.beminichat.dto.ConversationDto;
import com.eco.beminichat.response.base.ResponseEnable;

import java.util.ArrayList;
import java.util.Collection;

public class ConversationResponse extends ArrayList<ConversationDto> implements ResponseEnable {

    public ConversationResponse(Collection<? extends ConversationDto> c) {
        super(c);
    }
}
