package com.trulydesignfirm.namaha.model;

import com.trulydesignfirm.namaha.constant.DeliveryStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @Column(nullable = false)
    private Instant scheduledAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private LoginUser user;

    @ManyToOne
    @JoinColumn(name = "address_id", updatable = false, nullable = false)
    private Address address;

    @ManyToOne
    @JoinColumn(name = "product_id", updatable = false, nullable = false)
    private Product product;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @Column(nullable = false)
    private boolean active = true;

    public Delivery(Subscription subscription) {
        this.user = subscription.getUser();
        this.status = DeliveryStatus.PENDING;
        this.address = subscription.getAddress();
        this.product = subscription.getProduct();
        this.scheduledAt = LocalDate.now()
                .atTime(subscription.getDeliverySlot().getEnd())
                .atZone(ZoneId.systemDefault())
                .toInstant();
    }
}