package com.eco.beminichat.dto;

import com.eco.beminichat.response.base.ResponseEnable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ConversationDto implements ResponseEnable {

    protected Long id;

    protected String name;

    protected String avatar;

    protected MessageDto lastMessage;

    protected Boolean isGroup;

    public abstract boolean getIsGroup();

}

