package com.eco.beminichat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ConversationDto{

    protected Long id;

    protected String name;

    protected String avatar;

    protected MessageDto lastMessage;

    protected Boolean isGroup;

    public abstract boolean getIsGroup();

}

