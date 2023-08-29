package com.eco.beminichat.dto;

import lombok.*;

@Getter
@Setter
public class FriendDto extends AccountDto{

    private boolean isFriend;

    private boolean isRequestSent;

    public FriendDto(AccountDto accountDto) {
        super(
                accountDto.getId(),
                accountDto.getUsername(),
                accountDto.getDisplayName(),
                accountDto.getAvatarUrl(),
                accountDto.getLastOnline(),
                accountDto.getConversationId()
        );

        isFriend = false;
        isRequestSent = false;
    }

    public boolean isNotFriend() {
        return !isFriend;
    }
}
