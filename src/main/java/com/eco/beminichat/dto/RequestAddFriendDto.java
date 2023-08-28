package com.eco.beminichat.dto;

import com.eco.beminichat.enitities.Account;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Builder
public class RequestAddFriendDto {

    private Long id;

    private Boolean accepted;

    private AccountDto sender;

    private AccountDto receiver;

    private Timestamp createdAt;

}
