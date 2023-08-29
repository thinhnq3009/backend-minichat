package com.eco.beminichat.mapper;

import com.eco.beminichat.dto.FriendDto;
import com.eco.beminichat.enitities.Account;
import com.eco.beminichat.enitities.RequestAddFriend;
import com.eco.beminichat.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class FriendMapper implements Function<Account, FriendDto> {

    private final AccountMapper accountMapper;

    private final AccountService accountService;

    @Override
    public FriendDto apply(Account account) {
        FriendDto friendDto = new FriendDto(accountMapper.apply(account));

        Account authenticatedAccount = accountService.getAuthenticatedAccount();

        Optional<RequestAddFriend> isSent = authenticatedAccount
                .getRequestAddFriendsSent()
                .stream()
                .filter(
                        requestAddFriend -> requestAddFriend
                                .getReceiver()
                                .equals(account)
                ).findFirst();

        if (isSent.isPresent()) {
            friendDto.setRequestSent(true);
            friendDto.setFriend((isSent.get().getAccepted()));
        }  else {
            friendDto.setFriend(false);
            friendDto.setRequestSent(false);
        }

        return friendDto;
    }
}
