package com.eco.beminichat.mapper;

import com.eco.beminichat.dto.NotificationDto;
import com.eco.beminichat.enitities.Notification;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class NotificationMapper implements Function<Notification, NotificationDto> {
    @Override
    public NotificationDto apply(Notification notification) {
        return
                NotificationDto.builder()
                        .id(notification.getId())
                        .content(notification.getContent())
                        .posterUrl(notification.getPosterUrl())
                        .createdAt(notification.getCreatedAt())
                        .type(notification.getTypeNotification().toString())
                        .build()
                ;
    }
}
