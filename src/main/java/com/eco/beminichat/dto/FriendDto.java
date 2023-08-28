package com.eco.beminichat.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;
@Data
@Builder
public class FriendDto{
    private Long id;

    private String username;

    private String displayName;

    private String avatarUrl;

    private Boolean isSent;

}
