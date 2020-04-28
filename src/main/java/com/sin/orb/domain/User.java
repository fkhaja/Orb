package com.sin.orb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@Data
@EqualsAndHashCode(of = {"id"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified = false;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private AuthProvider provider;

    @Column(name = "provider_id")
    private String providerId;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<TaskCard> taskCards;
}
