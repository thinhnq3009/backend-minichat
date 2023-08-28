package com.eco.beminichat.enitities;

import java.sql.Timestamp;

public interface Notification {

    String getContent();

    String getPosterUrl();

    Timestamp getCreatedAt();

    Boolean getIsRead();


}
