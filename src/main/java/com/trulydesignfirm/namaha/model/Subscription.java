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
import java.time.temporal.ChronoUnit;
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

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliverySlot deliverySlot;

    @Column(nullable = false)
    private Instant startAt;

    @Column(nullable = false)
    private Instant endAt;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    public boolean isActive() {
        Instant now = Instant.now();
        return this.status == SubscriptionStatus.ACTIVE
                && now.isBefore(this.endAt);
    }

    public boolean isExpired() {
        Instant now = Instant.now();
        return now.isAfter(this.endAt) || this.status == SubscriptionStatus.EXPIRED;
    }

    public boolean isUpdatable() {
        Instant now = Instant.now();
        return (this.status == SubscriptionStatus.ACTIVE || this.status == SubscriptionStatus.PAUSED)
                && now.isBefore(this.endAt);
    }

    public Subscription(LoginUser user, Product product, SubscriptionStatus status, DeliverySlot deliverySlot, Address address) {
        this.user = user;
        this.product = product;
        this.status = status;
        this.deliverySlot = deliverySlot;
        this.address = address;
        Instant now = Instant.now();
        this.startAt = now;
        this.endAt = now.plus(product.getDurationInDays(), ChronoUnit.DAYS);
    }
}
