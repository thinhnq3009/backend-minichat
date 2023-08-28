package com.eco.beminichat.mapper;

import com.eco.beminichat.dto.ConversationDto;
import com.eco.beminichat.dto.GroupConversationDto;
import com.eco.beminichat.dto.SimpleConversationDto;
import com.eco.beminichat.enitities.Conversation;
import com.eco.beminichat.enitities.Message;
import com.eco.beminichat.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ConversationMapper implements Function<Conversation, ConversationDto> {

    private final AccountConversationMapper accountConversationMapper;

    private final AccountService accountService;

    private final MessageMapper messageMapper;

    @Override
    public ConversationDto apply(Conversation conversation) {

        List<Message> messages = conversation.getMessages();

        ConversationDto conversationDto;

        if (conversation.getIsGroup()) {
            conversationDto = new GroupConversationDto(
                    conversation
                            .getMembers(
                                    accountService.getAuthenticatedAccount()
                            )
                            .stream()
                            .map(accountConversationMapper)
                            .collect(Collectors.toList())
            );
        } else {
            conversationDto = new SimpleConversationDto(
                    conversation
                            .getTalker(
                                    accountService.getAuthenticatedAccount()
                            )
                            .map(accountConversationMapper)
                            .orElseThrow()
            );
        }

        conversationDto.setId(conversation.getId());
        conversationDto.setIsGroup(conversation.getIsGroup());
        conversationDto.setName(conversation.getName());
        conversationDto.setAvatar(conversation.getAvatar(accountService.getAuthenticatedAccount()));
        conversationDto.setLastMessage(
                messages
                        .stream()
                        .map(messageMapper)
                        .findFirst()
                        .orElse(null)
        );

        return conversationDto;
    }
}
