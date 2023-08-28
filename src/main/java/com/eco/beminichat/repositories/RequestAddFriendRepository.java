package com.eco.beminichat.repositories;

import com.eco.beminichat.enitities.RequestAddFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RequestAddFriendRepository extends JpaRepository<RequestAddFriend, Long> {

    RequestAddFriend findByReceiverIdAndSenderId(Long receiverId, Long senderId);

}