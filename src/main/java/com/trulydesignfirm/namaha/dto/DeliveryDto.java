package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.constant.DeliveryStatus;
import com.trulydesignfirm.namaha.model.Delivery;

import java.math.BigDecimal;
import java.util.UUID;

public record DeliveryDto(
        UUID id,
        DeliveryStatus status,
        BigDecimal finalPrice,
        DeliverySlotDto scheduledAt,
        AddressDto address
) {
    public DeliveryDto(Delivery delivery) {
        this(
                delivery.getId(),
                delivery.getStatus(),
                delivery.getFinalPrice(),
                new DeliverySlotDto(delivery.getSlot()),
                new AddressDto(delivery.getAddress())
        );
    }
}
