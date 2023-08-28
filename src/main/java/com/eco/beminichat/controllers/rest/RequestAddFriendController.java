package com.eco.beminichat.controllers.rest;

import com.eco.beminichat.dto.RequestAddFriendDto;
import com.eco.beminichat.enitities.RequestAddFriend;
import com.eco.beminichat.response.base.ResponseObject;
import com.eco.beminichat.response.base.ResponseObjects;
import com.eco.beminichat.services.AccountService;
import com.eco.beminichat.services.RequestAddFriendService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/api/v1/add-friend")
public class RequestAddFriendController {

    private final RequestAddFriendService requestAddFriendService;

    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/{requestId}")
    public ResponseEntity<ResponseObject<RequestAddFriendDto>> getRequest(
            @PathVariable Long requestId
    ) {

        RequestAddFriendDto requestAddFriend = requestAddFriendService.getRequest(requestId);

        return ResponseObjects.getResponseEntity(
             requestAddFriend,
                "Get request successfully"
        );
    }

    @PostMapping("/{receiverId}")
    public ResponseEntity<ResponseObject<Boolean>> addFriend(
            @PathVariable Long receiverId
    ) {

        RequestAddFriend requestAddFriend = requestAddFriendService.createRequest(receiverId);


        return ResponseObjects.getResponseEntity(true, "Add friend successfully");
    }

    @PostMapping("/accept/{requestId}")
    public ResponseEntity<ResponseObject<Boolean>> acceptRequest(
            @PathVariable Long requestId
    ) {

        requestAddFriendService.acceptRequest(requestId);

//        messagingTemplate.convertAndSendToUser(
//                requestAddFriend.getSender().getUsername(),
//                "/notification",
//                requestAddFriendService.requestAddFriendMapper.apply(requestAddFriend)
//        );

        return ResponseObjects.getResponseEntity(true, "Accept request successfully");
    }


}
