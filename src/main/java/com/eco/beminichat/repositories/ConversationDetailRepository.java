package com.eco.beminichat.repositories;

import com.eco.beminichat.enitities.ConversationDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationDetailRepository extends JpaRepository<ConversationDetail, Long> {
}