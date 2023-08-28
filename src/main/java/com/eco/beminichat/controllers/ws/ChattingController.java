package com.eco.beminichat.controllers.ws;

import com.eco.beminichat.enitities.Account;
import com.eco.beminichat.request.MessageRequest;
import com.eco.beminichat.response.SingleMessageResponse;
import com.eco.beminichat.services.JwtService;
import com.eco.beminichat.services.MessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

@Controller
@Slf4j
@AllArgsConstructor
public class ChattingController {

    private final SimpMessagingTemplate messagingTemplate;

    private final MessageService messageService;

    private final JwtService jwtService;

    @MessageMapping("/send-message")
    public SingleMessageResponse sendMessage(
            @Payload  MessageRequest message,
            Principal principal
            ) {

        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) principal;
        Account account = (Account) auth.getPrincipal();

        SingleMessageResponse response =  messageService.sendMessage(message, account);

        messagingTemplate.convertAndSendToUser(
                message.getConversationId().toString(),
                "/messages",
                response);


        return response;
    }
}
