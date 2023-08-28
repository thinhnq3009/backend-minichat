package com.eco.beminichat.response;

import com.eco.beminichat.dto.ConversationDto;
import com.eco.beminichat.enitities.Conversation;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConversationResponse extends ArrayList<ConversationDto>{

    public ConversationResponse(Collection<? extends ConversationDto> c) {
        super(c);
    }
}
