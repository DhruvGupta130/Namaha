package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.model.ProductCategory;

public record ProductCategoryDto(
        String category
) {
    public ProductCategoryDto(ProductCategory category) {
        this(category.getName());
    }
}
