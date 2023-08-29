package com.eco.beminichat.response;

import com.eco.beminichat.dto.AccountDto;
import com.eco.beminichat.dto.FriendDto;

import java.util.ArrayList;
import java.util.Collection;

public class ListAccountResponse extends ArrayList<FriendDto> {

    public ListAccountResponse(Collection<? extends FriendDto> c) {
        super(c);
    }
}
