package com.eco.beminichat.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleConversationDto extends  ConversationDto{

    private AccountConversationDto talker;


    @Override
    public boolean getIsGroup() {
        return false;
    }
}
