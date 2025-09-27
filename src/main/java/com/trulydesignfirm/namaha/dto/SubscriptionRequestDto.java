package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.constant.DeliverySlot;
import jakarta.validation.constraints.NotNull;

public record SubscriptionRequestDto(

        @NotNull(message = "Product is required!")
        Long productId,

        @NotNull(message = "Delivery Slot is required!")
        DeliverySlot slot,

        String couponCode,

        @NotNull(message = "Address is required!")
        Long addressId
) {}
