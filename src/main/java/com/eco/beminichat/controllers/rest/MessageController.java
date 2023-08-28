package com.eco.beminichat.controllers.rest;

import com.eco.beminichat.dto.MessageDto;
import com.eco.beminichat.exceptions.ConversationDenyAssessException;
import com.eco.beminichat.request.SendMessageRequest;
import com.eco.beminichat.response.MessagesResponse;
import com.eco.beminichat.response.base.ResponseObject;
import com.eco.beminichat.response.base.ResponseObjects;
import com.eco.beminichat.services.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/message")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("{conversationId}")
    public ResponseEntity<ResponseObject<MessagesResponse>> getMessage(
            @RequestParam(value = "page", required = false) Integer page,
            @PathVariable("conversationId") Long conversationId
    ) {

        page = page == null ? 0 : page;

        MessagesResponse response = messageService.getMessage(conversationId, page);

        return ResponseObjects.getResponseEntity(response, "Fetch messages successfully");
    }

    @PostMapping("/send")
    public ResponseEntity<ResponseObject<MessageDto>> sendMessage(
            @RequestBody SendMessageRequest request
            ) throws ConversationDenyAssessException {

        MessageDto response = messageService.sendMessage(request);

        messagingTemplate.convertAndSendToUser(
                request.getConversationId().toString(),
                "/messages",
                response
        );

        return ResponseObjects.getResponseEntity(response, "Send message successfully");
    }


}
