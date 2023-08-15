package com.eco.beminichat.enitities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "NVARCHAR(MAX)", nullable = false)
    private String content;

    @CreationTimestamp
    private Timestamp timestamp;

    @ManyToOne
    private Account sender;

    @ManyToOne
    private Conversation conversation;


}
