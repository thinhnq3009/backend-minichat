package com.eco.beminichat.dto;

import com.eco.beminichat.enitities.Account;
import lombok.*;

import java.util.List;

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
