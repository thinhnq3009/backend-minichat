package com.eco.beminichat.enitities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification")
public class SimpleNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, columnDefinition = "NVARCHAR(250)")
    String content;

    @ManyToOne
    Account owner;

    @CreationTimestamp
    Timestamp createdAt;

    @Column(nullable = false, columnDefinition = "BIT")
    Boolean isRead = false;

    @PrePersist
    protected void onCreate() {
        if (isRead == null) {
            isRead = false;
        }

        if (createdAt == null) {
            createdAt = new Timestamp(System.currentTimeMillis());
        }
    }



}
