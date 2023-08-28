package com.eco.beminichat.services;

import com.eco.beminichat.enitities.Account;
import com.eco.beminichat.exceptions.AccountNotFoundException;
import com.eco.beminichat.exceptions.AuthenticateException;
import com.eco.beminichat.mapper.AccountMapper;
//import com.eco.beminichat.mapper.FriendDtoMapper;
import com.eco.beminichat.repositories.AccountRepository;
import com.eco.beminichat.response.ListAccountResponse;
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

    private final AccountMapper accountMapper;
//    private final FriendDtoMapper friendDtoMapper;

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

//    public ListAccountResponse getAccountByQuery(String query, Pageable pageable) {
//        query = query.toLowerCase();
//        List<Account> accounts = accountRepository.findAccountContain(query, query, pageable);

//        if (accounts.isEmpty()) {
//            throw new AccountNotFoundException("Don't have any account contains \"%s\"".formatted(query));
//        }

//        return new ListAccountResponse(
//                accounts
//                        .stream()
//                        .map(friendDtoMapper)
//                        .collect(Collectors.toList())
//        );
//    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Not found account with id %d".formatted(id)));
    }
}
