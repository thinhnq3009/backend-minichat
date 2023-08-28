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
@Table(name = "request_add_friend")
public class RequestAddFriend  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "BIT")
    private Boolean accepted = false;

    @ManyToOne
    private Account sender;

    @ManyToOne
    private Account receiver;

    @CreationTimestamp
    private Timestamp createdAt;


    @PrePersist
    protected void onCreate() {
        if (accepted == null) {
            accepted = false;
        }

        if (createdAt == null) {
            createdAt = new Timestamp(System.currentTimeMillis());
        }
    }

}
