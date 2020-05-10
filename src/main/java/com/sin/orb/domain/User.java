package com.sin.orb.domain;

import com.sin.orb.exception.ResourceNotFoundException;
import com.sin.orb.security.AuthProvider;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Entity
@Data
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@EqualsAndHashCode(of = {"id"})
public class User implements OAuth2User, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull
    @Column(name = "username")
    private String username;

    @Email
    @NotNull
    @Column(name = "email")
    private String email;

    @Column(name = "image_url")
    private String imageUrl;

    @NotNull
    @Column(name = "email_verified")
    private Boolean emailVerified = false;

    @Column(name = "password")
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private AuthProvider provider;

    @Column(name = "provider_id")
    private String providerId;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<TaskCard> taskCards;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    @Transient
    private Map<String, Object> attributes;

    public TaskCard getTaskCard(Long id) {
        return taskCards.stream()
                        .filter(card -> card.getId().equals(id))
                        .findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException("Task card", "id", id));
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

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}
