package com.eco.beminichat.repositories;

import com.eco.beminichat.enitities.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(String username);

    @Query("""
            select a from Account a
            where upper(a.username) like upper(concat('%', ?1, '%')) or upper(a.displayName) like upper(concat('%', ?2, '%'))""")
    List<Account> findAccountContain(String username, String fullName, Pageable pageable);

}