package com.eco.beminichat.enitities;

import com.eco.beminichat.exceptions.ConversationDenyAssessException;
import com.eco.beminichat.values.DefaultValue;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "conversation")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "NVARCHAR(50)")
    private String name;

    @Column(nullable = false)
    private Boolean isGroup;

    @Column(columnDefinition = "VARCHAR(250)")
    private String avatar = DefaultValue.CONVERSATION_AVATAR;

    @OneToMany(mappedBy = "conversation")
    private List<ConversationDetail> conversationDetails = new ArrayList<>();

    @OneToMany(mappedBy = "conversation")
    private List<Message> messages = new ArrayList<>();

    @Transient
    private List<Account> members = new ArrayList<>();


    public String getAvatar(Account loggedUser) {
        if (isGroup) {
            return avatar == null ? DefaultValue.CONVERSATION_AVATAR : avatar;
        } else {
            return getMembers()
                    .stream()
                    .filter(account -> !account.equals(loggedUser))
                    .findFirst()
                    .map(Account::getAvatarUrl)
                    .orElse(DefaultValue.CONVERSATION_AVATAR);
        }
    }


    public List<Account> getMembers() {
        return getConversationDetails()
                .stream()
                .map(ConversationDetail::getAccount)
                .collect(Collectors.toList());
    }

    public List<Account> getMembers(Account loggedUser) {
        return getMembers()
                .stream()
                .filter(account -> !account.equals(loggedUser))
                .collect(Collectors.toList());
    }

    public Optional<Account> getTalker(Account loggedUser) {
        if (isGroup) {
            return Optional.empty();
        } else {
            return getMembers()
                    .stream()
                    .filter(account -> !account.equals(loggedUser))
                    .findFirst();
        }
    }

    public void checkConversationAccess(Account account) throws ConversationDenyAssessException {
        if (!getMembers().contains(account)) {
            throw new ConversationDenyAssessException(account);
        }
    }
}
