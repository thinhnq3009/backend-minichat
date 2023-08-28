package com.eco.beminichat.mapper;

import com.eco.beminichat.dto.AccountDto;
import com.eco.beminichat.enitities.Account;
import com.eco.beminichat.enitities.ConversationDetail;
import com.eco.beminichat.enitities.Message;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Function;

@Service
public class AccountMapper implements Function<Account, AccountDto> {

    @Override
    public AccountDto apply(Account account) {

        return AccountDto
                .builder()
                .id(account.getId())
                .avatarUrl(account.getAvatarUrl())
                .username(account.getUsername())
                .displayName(account.getDisplayName())
//                .lastOnline(
//                        account
//                                .getConversationDetails()
//                                .stream()
//                                .flatMap(
//                                        item -> item.getConversation().getMessages().stream()
//                                )
//                                .max(
//                                        Comparator.comparing(Message::getSentAt)
//                                )
//                                .map(
//                                        Message::getSentAt
//                                )
//                                .orElseGet(() -> new Timestamp(System.currentTimeMillis()))
//                )
//                .conversationId(account
//                        .getConversationDetails() == null
//                        ?
//                        new ArrayList<>()
//                        :
//                        account.getConversationDetails()
//                                .stream()
//                                .map(
//                                        item -> item.getConversation().getId()
//                                ).toList()
//                )
                .build();
    }
}
