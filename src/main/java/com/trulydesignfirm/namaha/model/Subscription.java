package com.trulydesignfirm.namaha.model;

import com.trulydesignfirm.namaha.constant.DeliverySlot;
import com.trulydesignfirm.namaha.constant.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(indexes = {
        @Index(name = "idx_user_status", columnList = "user_id, status")
})
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private LoginUser user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliverySlot deliverySlot;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    public boolean isActive() {
        return this.status == SubscriptionStatus.ACTIVE;
    }

    public boolean isExpired() {
        return this.status == SubscriptionStatus.EXPIRED;
    }

    public Subscription(LoginUser user, Product product, SubscriptionStatus status, DeliverySlot deliverySlot) {
        this.user = user;
        this.product = product;
        this.status = status;
        this.deliverySlot = deliverySlot;
    }
}
