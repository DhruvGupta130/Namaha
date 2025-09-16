package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.model.ProductVariety;

public record ProductVarietyDto(
        String variety
) {
    public ProductVarietyDto(ProductVariety variety) {
        this(variety.getName());
    }
}
