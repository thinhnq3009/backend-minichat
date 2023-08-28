package com.eco.beminichat.repositories;

import com.eco.beminichat.enitities.Account;
import com.eco.beminichat.enitities.Conversation;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("select c.conversation from ConversationDetail c where c.account = ?1")
    Page<Conversation> getConversationsByMembers(Account account, Pageable pageable);

}