package com.trulydesignfirm.namaha.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.trulydesignfirm.namaha.constant.DiscountType;
import com.trulydesignfirm.namaha.constant.OfferType;
import com.trulydesignfirm.namaha.model.Offers;

public record OfferDto(
        UUID id,
        String title,
        String description,
        String couponCode,
        BigDecimal minOrderAmount,
        BigDecimal discount,
        DiscountType discountType,
        OfferType offerType,
        Instant validFrom,
        Instant validUntil,
        boolean active
) {
    public OfferDto(Offers offer) {
        this(
                offer.getId(),
                offer.getTitle(),
                offer.getDescription(),
                offer.getCouponCode(),
                offer.getMinOrderAmount(),
                offer.getDiscount(),
                offer.getDiscountType(),
                offer.getOfferType(),
                offer.getValidFrom(),
                offer.getValidUntil(),
                offer.isActive()
        );
    }
}