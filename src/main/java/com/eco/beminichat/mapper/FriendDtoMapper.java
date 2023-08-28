package com.eco.beminichat.mapper;

import com.eco.beminichat.dto.FriendDto;
import com.eco.beminichat.enitities.Account;
import com.eco.beminichat.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class FriendDtoMapper implements Function<Account, FriendDto> {

    private final AccountService accountService;

    @Override
    public FriendDto apply(Account account) {

        Account loggedInAccount = accountService.getAuthenticatedAccount();

        return FriendDto.builder()
                .id(account.getId())
                .username(account.getUsername())
                .displayName(account.getDisplayName())
                .avatarUrl(account.getAvatarUrl())
                .isSent(
                        loggedInAccount.getRequestAddFriends()
                                .stream()
                                .anyMatch(request -> request.getReceiver().equals(account))
                )
                .build();
    }
}
