package com.eco.beminichat.repositories;

import com.eco.beminichat.enitities.SimpleNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<SimpleNotification, Long> {
}