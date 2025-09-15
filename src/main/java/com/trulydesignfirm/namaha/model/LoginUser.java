package com.trulydesignfirm.namaha.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trulydesignfirm.namaha.constant.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "login_user",
        indexes = {
                @Index(name = "idx_login_user_email", columnList = "email"),
                @Index(name = "idx_login_user_mobile", columnList = "mobile")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_login_user_email", columnNames = "email"),
                @UniqueConstraint(name = "uk_login_user_mobile", columnNames = "mobile")
        }
)
public class LoginUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String mobile;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Address address;
}