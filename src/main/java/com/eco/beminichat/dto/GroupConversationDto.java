package com.eco.beminichat.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupConversationDto extends ConversationDto{

    List<AccountConversationDto> members;


    @Override
    public boolean getIsGroup() {
        return true;
    }
}
