package com.eco.beminichat.config;

import com.eco.beminichat.services.AccountService;
import com.eco.beminichat.services.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.config.annotation.*;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
@AllArgsConstructor
@Slf4j
public class SocketConfiguration implements WebSocketMessageBrokerConfigurer {

    private final JwtService jwtService;

    private final AccountService accountService;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/user");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket")
                .setAllowedOrigins("http://localhost:3000")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {

                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {

                    List<String> headers = accessor.getNativeHeader("Authorization");

                    if (headers == null || headers.isEmpty()) {
                        return message;
                    }

                    String headerToken = headers.get(0);

                    if (headerToken == null || !headerToken.startsWith("Bearer ")) {
                        return message;
                    }

                    String token = headerToken.substring(7);

                    String username = jwtService.extractUsername(token);

                    UserDetails account = accountService.loadUserByUsername(username);

                    if (jwtService.validateToken(token, account)) {

                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                account,
                                null,
                                account.getAuthorities()
                        );

                        accessor.setUser(auth);



                        return MessageBuilder.createMessage(
                                message.getPayload(),
                                accessor.getMessageHeaders()
                        )     ;

                    }
                }
                return message;
            }
        });
    }


}
