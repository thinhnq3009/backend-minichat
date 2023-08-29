package com.eco.beminichat.dto;

import com.eco.beminichat.enitities.ConversationDetail;
import com.eco.beminichat.enitities.Message;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

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
