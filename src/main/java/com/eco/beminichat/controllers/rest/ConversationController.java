package com.eco.beminichat.controllers.rest;

import com.eco.beminichat.enitities.Conversation;
import com.eco.beminichat.response.ConversationResponse;
import com.eco.beminichat.response.base.ResponseObject;
import com.eco.beminichat.response.base.ResponseObjects;
import com.eco.beminichat.services.ConversationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/conversation")
@CrossOrigin("*")
@AllArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;

    @GetMapping()
    public ResponseEntity<ResponseObject<ConversationResponse>> getConversations(
            @RequestParam(required = false) Long page,
            @RequestParam(required = false) Long size
    ) {

        page = page == null ? 0 : page;
        size = size == null ? 10 : size;

        Pageable pageable = PageRequest.of(page.intValue(), size.intValue());


        ConversationResponse response = conversationService.getConversations(pageable);

        return ResponseObjects.getResponseEntity(response, "Conversations retrieved successfully");

    }

}
