package com.eco.beminichat.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class AccountDto {

    private Long id;

    private String username;

    private String displayName;

    private String avatarUrl;

    private Timestamp lastOnline;

    private List<Long> conversationId;



}
