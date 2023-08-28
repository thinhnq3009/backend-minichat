package com.eco.beminichat.enitities;

import com.eco.beminichat.auth.Role;
import com.eco.beminichat.dto.AccountDto;
import com.eco.beminichat.mapper.AccountMapper;
import com.eco.beminichat.values.DefaultValue;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@Table(name = "Account")
@NoArgsConstructor
@AllArgsConstructor
public class Account implements UserDetails, DataTransferObjectEnable<AccountDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, columnDefinition = "VARCHAR(50)")
    private String username;

    @Column(columnDefinition = "VARCHAR(250)", nullable = true)
    private String password;

    @Column(columnDefinition = "NVARCHAR(50)", nullable = true)
    private String displayName;

    @Column(columnDefinition = "VARCHAR(MAX)")
    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @OneToMany(mappedBy = "account")
    private List<ConversationDetail> conversationDetails = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<SimpleNotification> simpleNotifications = new ArrayList<>();

    @OneToMany(mappedBy = "receiver")
    private List<RequestAddFriend> requestAddFriends = new ArrayList<>();

    @PrePersist
    protected void setDefaultValues() {
        if (avatarUrl == null) {
            avatarUrl = DefaultValue.ACCOUNT_AVATAR;
        }

        if (role == null) {
            role = Role.USER;
        }
    }


    /*Methods implement by UserDetail*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(username, account.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    @Override
    public AccountDto toDTO() {
        return new AccountMapper().apply(this);
    }
}
