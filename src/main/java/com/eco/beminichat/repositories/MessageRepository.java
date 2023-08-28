package com.eco.beminichat.repositories;

import com.eco.beminichat.enitities.Conversation;
import com.eco.beminichat.enitities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("select m from Message m where m.conversation.id = ?1 order by m.sentAt desc")
    Page<Message> findAllByConversationId(Long conversationId, Pageable pageable);
}