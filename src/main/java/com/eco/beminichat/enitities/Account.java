package com.eco.beminichat.enitities;

import com.eco.beminichat.values.DefaultValue;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@Table(name = "Account")
@NoArgsConstructor
@AllArgsConstructor
public class Account implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, columnDefinition = "VARCHAR(50)")
    private String username;

    @Column(columnDefinition = "VARCHAR(250)", nullable = true)
    private String password;

    @Column(columnDefinition = "NVARCHAR(50)", nullable = true)
    private String displayName;

    @Column(columnDefinition = "VARCHAR(MAX)")
    private String avatarUrl;


    @OneToMany(mappedBy = "account")
    private List<ConversationDetail> conversationDetails = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private List<Message> messages = new ArrayList<>();


    @PrePersist
    protected void setDefaultValues() {
        if (avatarUrl  == null ) {
            avatarUrl = DefaultValue.ACCOUNT_AVATAR;
        }
    }




    /*Methods implement by UserDetail*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
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
}
