package com.eco.beminichat.services;

import com.eco.beminichat.enitities.Account;
import com.eco.beminichat.enitities.Conversation;
import com.eco.beminichat.enitities.ConversationDetail;
import com.eco.beminichat.repositories.ConversationDetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class ConversationDetailService {

    private final AccountService accountService;

    private final ConversationDetailRepository conversationDetailRepository;

    public List<ConversationDetail> createConversationDetails(
            Conversation conversation,
            Account... accounts
    ) {

        return conversationDetailRepository
                .saveAll(
                        Arrays.stream(accounts)
                                .map(account -> ConversationDetail
                                        .builder()
                                        .account(account)
                                        .conversation(conversation)
                                        .build()
                                )
                                .toList()
                                );

    }

}
