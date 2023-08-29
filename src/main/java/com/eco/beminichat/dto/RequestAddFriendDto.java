package com.eco.beminichat.dto;

import com.eco.beminichat.response.base.ResponseEnable;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class RequestAddFriendDto implements ResponseEnable {

    private Long id;

    private Boolean accepted;

    private AccountDto sender;

    private AccountDto receiver;

    private Timestamp createdAt;

}
