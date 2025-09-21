package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.constant.DeliveryStatus;
import com.trulydesignfirm.namaha.model.Delivery;

import java.time.Instant;
import java.util.UUID;

public record DeliveryDto(
        UUID id,
        DeliveryStatus status,
        Instant scheduledAt,
        AddressDto address
) {
    public DeliveryDto(Delivery delivery) {
        this(
                delivery.getId(),
                delivery.getStatus(),
                delivery.getScheduledAt(),
                new AddressDto(delivery.getAddress())
        );
    }
}
