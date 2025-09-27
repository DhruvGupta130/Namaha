package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.model.Offers;

import java.math.BigDecimal;
import java.util.UUID;

public record CustomerOfferDto(
        UUID id,
        String title,
        String description,
        String couponCode,
        BigDecimal discount
) {
    public CustomerOfferDto(Offers offer) {
        this(
                offer.getId(),
                offer.getTitle(),
                offer.getDescription(),
                offer.getCouponCode(),
                offer.getDiscount()
        );
    }
}
