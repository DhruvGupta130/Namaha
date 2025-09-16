package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.constant.SubscriptionStatus;
import com.trulydesignfirm.namaha.model.Subscription;

import java.time.Instant;
import java.util.UUID;

public record SubscriptionDto(
        UUID id,
        String userMobile,
        Long productId,
        String productTitle,
        SubscriptionStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public SubscriptionDto(Subscription subscription) {
        this(
                subscription.getId(),
                subscription.getUser().getMobile(),
                subscription.getProduct().getId(),
                subscription.getProduct().getTitle(),
                subscription.getStatus(),
                subscription.getCreatedAt(),
                subscription.getUpdatedAt()
        );
    }
}