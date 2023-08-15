package com.eco.beminichat.repositories;

import com.eco.beminichat.enitities.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}