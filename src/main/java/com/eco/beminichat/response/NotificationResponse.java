package com.eco.beminichat.response;

import com.eco.beminichat.dto.NotificationDto;
import com.eco.beminichat.response.base.ResponseEnable;

import java.util.ArrayList;
import java.util.Collection;

public class NotificationResponse extends ArrayList<NotificationDto> implements ResponseEnable {

    public NotificationResponse(Collection<? extends NotificationDto> c) {
        super(c);
    }
}
