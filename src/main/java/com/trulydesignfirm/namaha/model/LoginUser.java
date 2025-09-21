package com.trulydesignfirm.namaha.model;

import com.trulydesignfirm.namaha.annotation.Mobile;
import com.trulydesignfirm.namaha.constant.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
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
@NoArgsConstructor
public class LoginUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String email;

    @Column(nullable = false)
    private String mobile;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Subscription> subscriptions;

    public LoginUser(@Mobile String mobile) {
        this.mobile = mobile;
    }

    public boolean isProfileCompleted() {
        return name != null && !name.isBlank() &&
                addresses != null && !addresses.isEmpty();
    }
}