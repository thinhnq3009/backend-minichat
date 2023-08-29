package com.eco.beminichat.response;

import com.eco.beminichat.dto.FriendDto;
import com.eco.beminichat.response.base.ResponseEnable;

import java.util.ArrayList;
import java.util.Collection;

public class FindFriendResponse extends ArrayList<FriendDto> implements ResponseEnable {
    public FindFriendResponse(Collection<? extends FriendDto> c) {
        super(c);
    }
}
