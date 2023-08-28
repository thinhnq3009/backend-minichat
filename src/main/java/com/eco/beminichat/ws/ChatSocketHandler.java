package com.eco.beminichat.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.net.URL;


@Slf4j
public class ChatSocketHandler extends TextWebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String conversationId = getConversationId(session);

    }



    private String getConversationId(WebSocketSession session) {
       URI uri =  session.getUri();
        return  uri == null
                ? null
                : uri.getPath().replace("/chatting/", "");
    }

}
