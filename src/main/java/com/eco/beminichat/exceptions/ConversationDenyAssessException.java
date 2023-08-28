package com.eco.beminichat.exceptions;

import com.eco.beminichat.enitities.Account;

public class ConversationDenyAssessException extends Exception{
    public ConversationDenyAssessException(Account account) {
        super("Account <%s> is denied access to conversation".formatted(account.getDisplayName()));
    }

    public ConversationDenyAssessException(String username) {
        super("Account <%s> is denied access to conversation".formatted(username));
    }
}
