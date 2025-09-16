package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.model.Product;
import com.trulydesignfirm.namaha.model.ProductCategory;
import com.trulydesignfirm.namaha.model.ProductVariety;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record ProductDto(
        Long id,

        @NotBlank(message = "Product title is required")
        String title,

        @NotNull(message = "Images list cannot be null")
        @Size(min = 1, max = 3, message = "Product must have between 1 and 3 images")
        List<@NotBlank(message = "Image URL cannot be blank") String> images,

        @NotBlank(message = "Product description is required")
        String description,

        @NotNull(message = "Weight is required")
        @Min(value = 1, message = "Weight must be at least 1 gram")
        Integer weightInGrams,

        @NotNull(message = "Flower variety is required")
        ProductVariety variety,

        @NotNull(message = "Product Category is required")
        ProductCategory category,

        @DecimalMin(value = "0.01", message = "Subscription price must be greater than 0")
        BigDecimal subscriptionPrice,

        @NotNull(message = "Duration in days is required")
        @Min(value = 0, message = "Duration cannot be negative")
        Integer durationInDays,

        @NotNull(message = "One-time price is required")
        @DecimalMin(value = "0.01", message = "One-time price must be greater than 0")
        BigDecimal oneTimePrice,

        @NotNull(message = "One-time purchase flag is required")
        Boolean oneTime
) {
    public ProductDto(Product product) {
        this(
                product.getId(),
                product.getTitle(),
                product.getImages(),
                product.getDescription(),
                product.getWeightInGrams(),
                product.getVariety(),
                product.getCategory(),
                product.getSubscriptionPrice(),
                product.getDurationInDays(),
                product.getOneTimePrice(),
                product.getOneTime()
        );
    }
}