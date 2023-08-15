package com.eco.beminichat.repositories;

import com.eco.beminichat.enitities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}