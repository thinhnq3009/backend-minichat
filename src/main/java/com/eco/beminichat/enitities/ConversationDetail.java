package com.eco.beminichat.enitities;

import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@Builder
@Entity
@Table(name = "conversation_detail")
@NoArgsConstructor
@AllArgsConstructor
public class ConversationDetail {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Conversation conversation;

    @ManyToOne
    private Account account;

}
