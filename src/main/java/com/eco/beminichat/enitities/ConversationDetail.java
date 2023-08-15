package com.eco.beminichat.enitities;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.catalina.User;

@Data
@Entity
@Table(name = "conversation_detail")
public class ConversationDetail {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Conversation conversation;

    @ManyToOne
    private Account account;

}
