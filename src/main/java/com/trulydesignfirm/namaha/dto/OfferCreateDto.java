package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.constant.DiscountType;
import com.trulydesignfirm.namaha.constant.OfferType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Instant;

public record OfferCreateDto(
        @NotBlank(message = "Title is required")
        String title,

        String description,

        @NotBlank(message = "Coupon code is required")
        String couponCode,

        @NotNull(message = "Minimum order amount is required")
        @Positive(message = "Minimum order amount must be greater than zero")
        BigDecimal minOrderAmount,

        @NotNull(message = "Discount is required")
        BigDecimal discount,

        @NotNull(message = "Discount type is required")
        DiscountType discountType,

        @NotNull(message = "Offer type is required")
        OfferType offerType,

        @NotNull(message = "Valid From must not be null")
        Instant validFrom,

        @NotNull(message = "Valid Until must not be null")
        Instant validUntil
) {}