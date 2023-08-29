package com.eco.beminichat.repositories;

import com.eco.beminichat.enitities.Account;
import com.eco.beminichat.enitities.RequestAddFriend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RequestAddFriendRepository requestAddFriendRepository;

    @Test
    public void testFindAccountContain() {
        Account sender = new Account();
        sender.setUsername("sender");
        sender.setDisplayName("Sender User");
        accountRepository.save(sender);

        Account receiver1 = new Account();
        receiver1.setUsername("receiver1");
        receiver1.setDisplayName("Receiver User 1");
        accountRepository.save(receiver1);

        Account receiver2 = new Account();
        receiver2.setUsername("receiver2");
        receiver2.setDisplayName("Receiver User 2");
        accountRepository.save(receiver2);

        RequestAddFriend request = new RequestAddFriend();
        request.setSender(sender);
        request.setReceiver(receiver1);
        request.setAccepted(false);
        requestAddFriendRepository.save(request);

        List<Account> accounts = accountRepository.findAccountContain("", "", sender, Pageable.unpaged());
        assertThat(accounts).hasSize(2);
        assertThat(accounts).extracting(Account::getDisplayName)
                .containsExactlyInAnyOrder("Sender User", "Receiver User 2");
    }
}
