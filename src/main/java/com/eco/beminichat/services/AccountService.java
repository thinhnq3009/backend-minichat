package com.eco.beminichat.services;

import com.eco.beminichat.dto.FriendDto;
import com.eco.beminichat.enitities.Account;
import com.eco.beminichat.exceptions.AccountNotFoundException;
import com.eco.beminichat.exceptions.AuthenticateException;
import com.eco.beminichat.mapper.AccountMapper;
//import com.eco.beminichat.mapper.FriendDtoMapper;
import com.eco.beminichat.mapper.FriendMapper;
import com.eco.beminichat.repositories.AccountRepository;
import com.eco.beminichat.response.FindFriendResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;


    public Account getAuthenticatedAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticateException("Request not authenticated");
        }
        return (Account) authentication.getPrincipal();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    public FindFriendResponse getAccountByQuery(String query, Pageable pageable) {
        query = query.toLowerCase();
        List<Account> accounts = accountRepository.findAccountContain(
                query,
                query,
                getAuthenticatedAccount(),
                pageable);

        if (accounts.isEmpty()) {
            throw new AccountNotFoundException("Don't have any account contains \"%s\"".formatted(query));
        }

        FriendMapper friendMapper = new FriendMapper(new AccountMapper(), this);

        return new FindFriendResponse(
                accounts
                        .stream()
                        .filter(account -> !account.equals(getAuthenticatedAccount()))
                        .map(friendMapper)
                        .filter(FriendDto::isNotFriend)
                        .collect(Collectors.toList())
        );
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Not found account with id %d".formatted(id)));
    }
}
