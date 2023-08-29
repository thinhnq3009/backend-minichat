package com.eco.beminichat.services;

import com.eco.beminichat.dto.ConversationDto;
import com.eco.beminichat.dto.RequestAddFriendDto;
import com.eco.beminichat.enitities.Account;
import com.eco.beminichat.enitities.Conversation;
import com.eco.beminichat.enitities.RequestAddFriend;
import com.eco.beminichat.exceptions.RequestAddFriendException;
import com.eco.beminichat.mapper.ConversationMapper;
import com.eco.beminichat.mapper.NotificationMapper;
import com.eco.beminichat.mapper.RequestAddFriendMapper;
import com.eco.beminichat.repositories.RequestAddFriendRepository;
import com.eco.beminichat.response.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestAddFriendService {

    private final AccountService accountService;

    private final ConversationService conversationService;

    private final ConversationMapper conversationMapper;

    private final RequestAddFriendRepository addFriendRepository;

    private final SimpMessagingTemplate messagingTemplate;

    private final RequestAddFriendMapper requestAddFriendMapper;

    private final NotificationMapper notificationMapper;

    public RequestAddFriend createRequest(Long receiverId) {

        Account sender = accountService.getAuthenticatedAccount();

        RequestAddFriend requested = addFriendRepository.findByReceiverIdAndSenderId(receiverId, sender.getId());

        if (requested != null) {
            if (requested.getAccepted()) {
                throw new RequestAddFriendException("You are already friend with this person");
            }

            throw new RequestAddFriendException("You have already sent a request to this person");
        }


        Account receiver = accountService.getAccountById(receiverId);


        RequestAddFriend request =
                RequestAddFriend.builder()
                .sender(sender)
                .receiver(receiver)
                .build();

        addFriendRepository.save(request);

        messagingTemplate.convertAndSendToUser(
                receiver.getUsername(),
                "/notification",
                requestAddFriendMapper.apply(request)
        );

        return request;
    }

    public ConversationDto acceptRequest(Long requestId) {
        RequestAddFriend request = addFriendRepository
                .findById(requestId)
                .orElseThrow(
                        () -> new RequestAddFriendException("Request not found")
                );

        Account loggedUser = accountService.getAuthenticatedAccount();

        if (!request.getReceiver().equals(loggedUser)) {
            throw new RequestAddFriendException("You can't accept this request");
        }

        if (request.getAccepted()) {
            throw new RequestAddFriendException("This request has already been accepted");
        }


     Conversation conversation = conversationService.createConversation(
               false,
                request.getSender(),
                request.getReceiver()
        );


        request.setAccepted(true);

        addFriendRepository.save(request);

        return conversationMapper.apply(conversation);

    }

    public RequestAddFriendDto getRequest(Long requestId) {
       return addFriendRepository.findById(requestId)
                .map(requestAddFriendMapper)
                .orElseThrow(
                        () -> new RequestAddFriendException("Request add friend not found")
                );
    }

    public NotificationResponse getAllRequest(Pageable pageable) {

        Account loggedUser = accountService.getAuthenticatedAccount();

        Page<RequestAddFriend> requestFriends = addFriendRepository.findByReceiverAndAcceptedFalse(loggedUser, pageable);

        return new NotificationResponse(
                requestFriends
                        .stream()
                        .map(notificationMapper)
                        .collect(Collectors.toList())
        );

    }

    public void deleteRequest(Long requestId) {

        RequestAddFriend addFriend = addFriendRepository.findById(requestId)
                .orElseThrow(
                        () -> new RequestAddFriendException("Request add friend not found")
                );

        Account loggedUser = accountService.getAuthenticatedAccount();

        if (!addFriend.getReceiver().equals(loggedUser)) {
            throw new RequestAddFriendException("You can't delete this request");
        }

        addFriendRepository.delete(addFriend);

    }
}
