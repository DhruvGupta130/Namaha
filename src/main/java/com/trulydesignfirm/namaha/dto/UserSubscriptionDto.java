package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.constant.SubscriptionStatus;
import com.trulydesignfirm.namaha.model.Subscription;

import java.time.Instant;
import java.util.UUID;

public record UserSubscriptionDto(
        UUID id,
        UUID userId,
        String name,
        Long productId,
        String productTitle,
        String productImage,
        SubscriptionStatus status,
        AddressDto address,
        DeliverySlotDto slot,
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
                subscription.getStatus(),
                new AddressDto(subscription.getAddress()),
                new DeliverySlotDto(subscription.getDeliverySlot()),
                subscription.getStartAt(),
                subscription.getEndAt()
        );
    }
}
