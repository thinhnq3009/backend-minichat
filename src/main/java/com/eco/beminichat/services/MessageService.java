package com.eco.beminichat.services;

import com.eco.beminichat.dto.MessageDto;
import com.eco.beminichat.enitities.Account;
import com.eco.beminichat.enitities.Conversation;
import com.eco.beminichat.enitities.Message;
import com.eco.beminichat.exceptions.ConversationDenyAssessException;
import com.eco.beminichat.exceptions.ConversationNotFoundException;
import com.eco.beminichat.mapper.AccountConversationMapper;
import com.eco.beminichat.mapper.MessageMapper;
import com.eco.beminichat.repositories.MessageRepository;
import com.eco.beminichat.request.MessageRequest;
import com.eco.beminichat.request.SendMessageRequest;
import com.eco.beminichat.response.MessagesResponse;
import com.eco.beminichat.response.SingleMessageResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ConcurrentModificationException;

@Service
@AllArgsConstructor
@Slf4j
public class MessageService {

    private final MessageRepository messageRepository;

    private final AccountConversationMapper accountMapper;

    private final AccountService accountService;

    private final MessageMapper messageMapper;

    private final ConversationService conversationService;

    public MessagesResponse getMessage(Long conversationId, Integer page) {

        Pageable pageable = Pageable.ofSize(100).withPage(page);


        Page<Message> messages = messageRepository.findAllByConversationId(conversationId, pageable);


        return new MessagesResponse(
                messages
                        .map(messageMapper)
                        .toList()
        );

    }

    // Message have been sent by http protocol
    public MessageDto sendMessage(SendMessageRequest request) throws ConversationDenyAssessException {

        Account account = accountService.getAuthenticatedAccount();

        Conversation conversation = conversationService
                .findConversationById(request.getConversationId())
                .orElseThrow(() -> new ConcurrentModificationException("Conversation not found"));

        conversation.checkConversationAccess(account);

        Message message = Message
                .builder()
                .content(request.getMessage())
                .conversation(conversation)
                .sender(account)
                .sentAt(new Timestamp(System.currentTimeMillis()))
                .build();

        messageRepository.save(message);

        return messageMapper.apply(message);

    }

    public SingleMessageResponse sendMessage(MessageRequest request, Account sender) {

        Conversation conversation = conversationService
                .findConversationById(request.getConversationId())
                .orElseThrow(
                        () -> new ConversationNotFoundException(
                                "Not found conversation have id: " + request.getConversationId()
                        )
                );

        Message message = Message
                .builder()
                .conversation(conversation)
                .sender(sender)
                .content(request.getContent())
                .sentAt(new Timestamp(System.currentTimeMillis()))
                .build();

        messageRepository.saveAndFlush(message);

        log.info("Message have been saved");

        return SingleMessageResponse
                .builder()
                .message(
                        MessageDto
                                .builder()
                                .content(message.getContent())
                                .sentAt(message.getSentAt())
                                .isMine(message.getIsMine(sender))
                                .sender(
                                        accountMapper.apply(sender)
                                )
                                .build()
                )
                .conversationId(request.getConversationId())
                .build();
    }

}
