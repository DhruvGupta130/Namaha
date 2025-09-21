package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.constant.DeliveryStatus;
import com.trulydesignfirm.namaha.model.Delivery;

import java.time.Instant;
import java.util.UUID;

public record DeliveryInfoDto(
        UUID id,
        DeliveryStatus status,
        Instant scheduledAt,
        AddressDto address,
        String customerName,
        String customerMobile
) {
    public DeliveryInfoDto(Delivery delivery) {
        this(
                delivery.getId(),
                delivery.getStatus(),
                delivery.getScheduledAt(),
                new AddressDto(delivery.getAddress()),
                delivery.getUser().getName(),
                delivery.getUser().getMobile()
        );
    }
}