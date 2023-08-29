package com.eco.beminichat.enitities;

import com.eco.beminichat.enums.TypeNotification;

import java.sql.Timestamp;

public interface Notification {

    Long getId();

    String getContent();

    String getPosterUrl();

    Timestamp getCreatedAt();

    TypeNotification getTypeNotification();


}
