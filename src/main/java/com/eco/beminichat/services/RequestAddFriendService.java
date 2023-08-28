package com.eco.beminichat.services;

import com.eco.beminichat.dto.RequestAddFriendDto;
import com.eco.beminichat.enitities.Account;
import com.eco.beminichat.enitities.Conversation;
import com.eco.beminichat.enitities.RequestAddFriend;
import com.eco.beminichat.exceptions.RequestAddFriendException;
import com.eco.beminichat.mapper.RequestAddFriendMapper;
import com.eco.beminichat.repositories.RequestAddFriendRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RequestAddFriendService {

    private final AccountService accountService;

    private final ConversationService conversationService;

    private final RequestAddFriendRepository addFriendRepository;

    private final SimpMessagingTemplate messagingTemplate;

    private final RequestAddFriendMapper requestAddFriendMapper;

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

    public void acceptRequest(Long requestId) {
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


        conversationService.createConversation(
               false,
                request.getSender(),
                request.getReceiver()
        );


        request.setAccepted(true);

        addFriendRepository.save(request);
    }

    public RequestAddFriendDto getRequest(Long requestId) {
       return addFriendRepository.findById(requestId)
                .map(requestAddFriendMapper)
                .orElseThrow(
                        () -> new RequestAddFriendException("Request add friend not found")
                );
    }
}
