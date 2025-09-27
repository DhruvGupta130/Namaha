package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.constant.SubscriptionStatus;
import com.trulydesignfirm.namaha.model.Subscription;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public record SubscriptionDto(
        UUID id,
        Long productId,
        String productTitle,
        SubscriptionStatus status,
        AddressDto address,
        DeliverySlotDto slot,
        OfferDto offer,
        Instant startAt,
        Instant endAt
) {
    public SubscriptionDto(Subscription subscription) {
        this(
                subscription.getId(),
                subscription.getProduct().getId(),
                subscription.getProduct().getTitle(),
                subscription.getStatus(),
                new AddressDto(subscription.getAddress()),
                new DeliverySlotDto(subscription.getDeliverySlot()),
                Optional.ofNullable(subscription.getOffer()).map(OfferDto::new).orElse(null),
                subscription.getStartAt(),
                subscription.getEndAt()
        );
    }
}