package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.constant.DeliveryStatus;
import com.trulydesignfirm.namaha.model.Delivery;

import java.math.BigDecimal;
import java.util.UUID;

public record DeliveryInfoDto(
        UUID id,
        DeliveryStatus status,
        DeliverySlotDto scheduledAt,
        AddressDto address,
        String customerName,
        String customerMobile,
        ProductDto product,
        BigDecimal finalPrice
) {
    public DeliveryInfoDto(Delivery delivery) {
        this(
                delivery.getId(),
                delivery.getStatus(),
                new DeliverySlotDto(delivery.getSlot()),
                new AddressDto(delivery.getAddress()),
                delivery.getUser().getName(),
                delivery.getUser().getMobile(),
                new ProductDto(delivery.getProduct()),
                delivery.getFinalPrice()
        );
    }
}