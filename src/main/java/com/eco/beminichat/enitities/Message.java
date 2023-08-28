package com.eco.beminichat.enitities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@Table(name = "message")
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "NVARCHAR(MAX)", nullable = false)
    private String content;

    @CreationTimestamp
    private Timestamp sentAt;

    @ManyToOne
    private Account sender;

    @ManyToOne
    private Conversation conversation;


    @PrePersist
    protected void onCreate() {
        if (sentAt == null ) {
            sentAt = new Timestamp(System.currentTimeMillis());
        }
    }

    public Boolean getIsMine(Account loggedUser) {
        return sender.equals(loggedUser);
    }
}
