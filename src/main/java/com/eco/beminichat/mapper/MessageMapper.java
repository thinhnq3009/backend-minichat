package com.eco.beminichat.mapper;

import com.eco.beminichat.dto.MessageDto;
import com.eco.beminichat.enitities.Account;
import com.eco.beminichat.enitities.Message;
import com.eco.beminichat.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class MessageMapper implements Function<Message, MessageDto> {

    private final AccountConversationMapper accountMapper;

    private final AccountService accountService;

    @Override
    public MessageDto apply(Message message) {

        Account loggedInAccount = accountService.getAuthenticatedAccount();

        return MessageDto
                .builder()
                .content(message.getContent())
                .sentAt(message.getSentAt())
                .isMine(message.getIsMine(loggedInAccount))
                .sender(
                        accountMapper.apply(message.getSender())
                )
                .build();
    }
}
