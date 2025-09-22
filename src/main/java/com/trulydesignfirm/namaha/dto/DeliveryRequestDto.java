package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.constant.DeliverySlot;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DeliveryRequestDto(
        @NotNull(message = "Product is required!")
        Long productId,

        @NotNull(message = "Delivery Date is required!")
        LocalDate deliveryDate,

        @NotNull(message = "Delivery Slot is required!")
        DeliverySlot slot,

        @NotNull(message = "Address is required!")
        Long addressId
) {
}
