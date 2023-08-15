package com.eco.beminichat.mapper;

import com.eco.beminichat.dto.AccountDto;
import com.eco.beminichat.enitities.Account;
import com.eco.beminichat.enitities.ConversationDetail;

import java.util.ArrayList;
import java.util.function.Function;

public class AccountMapper implements Function<Account, AccountDto> {


    @Override
    public AccountDto apply(Account account) {

        return AccountDto
                .builder()
                .id(account.getId())
                .avatarUrl(account.getAvatarUrl())
                .username(account.getUsername())
                .displayName(account.getDisplayName())
                .conversationId(account
                        .getConversationDetails() == null
                        ?
                        new ArrayList<>()
                        :
                        account.getConversationDetails()
                                .stream()
                                .map(
                                        ConversationDetail::getId
                                ).toList()
                )
                .build();
    }
}
