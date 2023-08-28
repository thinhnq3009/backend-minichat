package com.eco.beminichat.mapper;

import com.eco.beminichat.dto.RequestAddFriendDto;
import com.eco.beminichat.enitities.RequestAddFriend;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class RequestAddFriendMapper implements Function<RequestAddFriend, RequestAddFriendDto> {

    private final AccountMapper accountMapper;

    @Override
    public RequestAddFriendDto apply(RequestAddFriend requestAddFriend) {
        return RequestAddFriendDto
                .builder()
                .id(requestAddFriend.getId())
                .sender(
                        accountMapper.apply(requestAddFriend.getSender())
                )
                .receiver(
                        accountMapper.apply(requestAddFriend.getReceiver())
                )
                .accepted(requestAddFriend.getAccepted())
                .createdAt(requestAddFriend.getCreatedAt())
                .build();
    }
}
