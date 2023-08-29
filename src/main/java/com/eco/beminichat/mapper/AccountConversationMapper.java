package com.eco.beminichat.mapper;

import com.eco.beminichat.dto.AccountConversationDto;
import com.eco.beminichat.enitities.Account;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AccountConversationMapper implements Function<Account, AccountConversationDto> {

    @Override
    public AccountConversationDto apply(Account account) {
        return AccountConversationDto
                .builder()
                .avatarUrl(account.getAvatarUrl())
                .username(account.getUsername())
                .displayName(account.getDisplayName())
                .build();
    }
}
