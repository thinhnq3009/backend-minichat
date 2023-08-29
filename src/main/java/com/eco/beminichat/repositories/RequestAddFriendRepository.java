package com.eco.beminichat.repositories;

import com.eco.beminichat.enitities.Account;
import com.eco.beminichat.enitities.RequestAddFriend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestAddFriendRepository extends JpaRepository<RequestAddFriend, Long> {
    Page<RequestAddFriend> findByReceiverAndAcceptedFalse(Account receiver, Pageable pageable);

    RequestAddFriend findByReceiverIdAndSenderId(Long receiverId, Long senderId);

}