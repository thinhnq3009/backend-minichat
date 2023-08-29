package com.eco.beminichat.controllers.rest;

import com.eco.beminichat.dto.ConversationDto;
import com.eco.beminichat.dto.RequestAddFriendDto;
import com.eco.beminichat.enitities.RequestAddFriend;
import com.eco.beminichat.response.BooleanResponse;
import com.eco.beminichat.response.FindFriendResponse;
import com.eco.beminichat.response.NotificationResponse;
import com.eco.beminichat.response.base.ResponseObject;
import com.eco.beminichat.response.base.ResponseObjects;
import com.eco.beminichat.services.AccountService;
import com.eco.beminichat.services.RequestAddFriendService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/add-friend")
public class RequestAddFriendController {

    private final RequestAddFriendService requestAddFriendService;

    private final SimpMessagingTemplate messagingTemplate;

    private final AccountService accountService;


    @GetMapping()
    public ResponseEntity<ResponseObject<NotificationResponse>> getRequests(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer limit
    ) {

        Pageable pageable = Pageable.ofSize(limit).withPage(page);

        NotificationResponse response = requestAddFriendService.getAllRequest(pageable);

        return ResponseObjects.getResponseEntity(response, "Get requests successfully");


    }

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
    public ResponseEntity<ResponseObject<BooleanResponse>> addFriend(
            @PathVariable Long receiverId
    ) {

        RequestAddFriend requestAddFriend = requestAddFriendService.createRequest(receiverId);


        return ResponseObjects.getResponseEntity(BooleanResponse.of(true), "Add friend successfully");
    }

    @PostMapping("/accept/{requestId}")
    public ResponseEntity<ResponseObject<ConversationDto>> acceptRequest(
            @PathVariable Long requestId
    ) {

      ConversationDto response =  requestAddFriendService.acceptRequest(requestId);

//        messagingTemplate.convertAndSendToUser(
//                requestAddFriend.getSender().getUsername(),
//                "/notification",
//                requestAddFriendService.requestAddFriendMapper.apply(requestAddFriend)
//        );

        return ResponseObjects.getResponseEntity(
                response,
                "Accept request successfully"
        );
    }




    @GetMapping("/find")
    public ResponseEntity<ResponseObject<FindFriendResponse>> getAccounts(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer limit
    ) {

        Pageable pageable = Pageable.ofSize(limit).withPage(page);

        FindFriendResponse response = accountService.getAccountByQuery(query, pageable);

        return ResponseObjects.getResponseEntity(response, "Get accounts successfully");
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<ResponseObject<BooleanResponse>> deleteRequest(
            @PathVariable Long requestId
    ) {

        requestAddFriendService.deleteRequest(requestId);

        return ResponseObjects.getResponseEntity(
                BooleanResponse.of(true),
                "Delete request successfully"
        );
    }
}
