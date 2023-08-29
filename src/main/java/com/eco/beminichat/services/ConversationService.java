package com.eco.beminichat.services;

import com.eco.beminichat.enitities.Account;
import com.eco.beminichat.enitities.Conversation;
import com.eco.beminichat.mapper.ConversationMapper;
import com.eco.beminichat.repositories.ConversationRepository;
import com.eco.beminichat.response.ConversationResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ConversationService {

    private final AccountService accountService;

    private final ConversationRepository conversationRepository;

    private final ConversationDetailService conversationDetailService;

    private final ConversationMapper conversationMapper;


    public ConversationResponse getConversations(Pageable pageable) {

        Account account = accountService.getAuthenticatedAccount();

        Page<Conversation> conversations = conversationRepository.getConversationsByMembers(account, pageable);

        return new ConversationResponse(
                conversations
                        .map(item -> {
                            if (!item.getIsGroup()) {
                                Optional<Account> talkerOtn = item
                                        .getTalker(account);

                                if (talkerOtn.isPresent()) {
                                    Account talker = talkerOtn.get();
                                    item.setAvatar(talker.getAvatarUrl());
                                    item.setName(talker.getDisplayName());
                                }


                            }
                            return conversationMapper.apply(item);
                        })
                        .toList()
        );
    }

    public Optional<Conversation> findConversationById(Long conversationId) {
        return conversationRepository.findById(conversationId);
    }


    public Conversation createConversation(
            boolean isGroup,
            Account... members
    ) {

        if (members.length < 2) {
            throw new IllegalArgumentException("Conversation must have at least 2 members");
        }

        isGroup = isGroup || members.length > 2;


        Conversation conversation = conversationRepository
                .saveAndFlush(
                        Conversation.builder()
                                .isGroup(isGroup)
                                .build()
                );

        conversation.setConversationDetails(
                conversationDetailService.createConversationDetails(
                        conversation,
                        members
                )
        );

       return conversationRepository.save(conversation);

    }
}
