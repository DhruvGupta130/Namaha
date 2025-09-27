package com.trulydesignfirm.namaha.model;

import com.trulydesignfirm.namaha.constant.DiscountType;
import com.trulydesignfirm.namaha.constant.OfferType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "offers",
        indexes = {
                @Index(name = "idx_coupon_code", columnList = "couponCode"),
                @Index(name = "idx_title", columnList = "title"),
                @Index(name = "idx_active", columnList = "active")
        }
)
public class Offers {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String couponCode;

    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal minOrderAmount; // minimum order to apply coupon

    @Column(precision = 19, scale = 2)
    private BigDecimal discount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OfferType offerType;

    @Column(nullable = false)
    private Instant validFrom;

    @Column(nullable = false)
    private Instant validUntil;

    private boolean active = true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}
