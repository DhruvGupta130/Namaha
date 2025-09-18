package com.trulydesignfirm.namaha.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trulydesignfirm.namaha.model.Product;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record ProductDto(

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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
        String variety,

        @NotNull(message = "Product Category is required")
        String category,

        BigDecimal subscriptionPrice,

        Integer durationInDays,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Boolean isSubscription,

        BigDecimal oneTimePrice,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Boolean isOneTime
) {
    public ProductDto(Product product) {
        this(
                product.getId(),
                product.getTitle(),
                product.getImages(),
                product.getDescription(),
                product.getWeightInGrams(),
                product.getVariety().getName(),
                product.getCategory().getName(),
                product.getSubscriptionPrice(),
                product.getDurationInDays(),
                product.isSubscription(),
                product.getOneTimePrice(),
                product.isOneTimePurchase()
        );
    }
}