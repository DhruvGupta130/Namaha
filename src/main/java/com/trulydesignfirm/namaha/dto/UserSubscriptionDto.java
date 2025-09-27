package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.constant.SubscriptionStatus;
import com.trulydesignfirm.namaha.model.Subscription;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public record UserSubscriptionDto(
        UUID id,
        UUID userId,
        String name,
        Long productId,
        String productTitle,
        String productImage,
        BigDecimal productPrice,
        SubscriptionStatus status,
        AddressDto address,
        DeliverySlotDto slot,
        OfferDto offer,
        Instant startAt,
        Instant endAt
) {
    public UserSubscriptionDto(Subscription subscription) {
        this(
                subscription.getId(),
                subscription.getUser().getId(),
                subscription.getUser().getName(),
                subscription.getProduct().getId(),
                subscription.getProduct().getTitle(),
                subscription.getProduct().getImages().getFirst(),
                subscription.getProduct().getSubscriptionPrice(),
                subscription.getStatus(),
                new AddressDto(subscription.getAddress()),
                new DeliverySlotDto(subscription.getDeliverySlot()),
                Optional.ofNullable(subscription.getOffer()).map(OfferDto::new).orElse(null),
                subscription.getStartAt(),
                subscription.getEndAt()
        );
    }
}
