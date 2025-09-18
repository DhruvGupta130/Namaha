package com.trulydesignfirm.namaha.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(indexes = {
        @Index(name = "idx_product_variety", columnList = "variety"),
        @Index(name = "idx_product_category", columnList = "category"),
        @Index(name = "idx_product_active", columnList = "active"),
        @Index(name = "idx_category_active", columnList = "category, active")
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "product_images",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Column(name = "image_url", nullable = false)
    @Size(min = 1, max = 3, message = "Product must have between 1 and 3 images")
    private List<String> images = new ArrayList<>();

    @Column(nullable = false)
    private Integer weightInGrams;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "variety", nullable = false)
    private ProductVariety variety;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category", nullable = false)
    private ProductCategory category;

    private Integer durationInDays;

    @Column(precision = 10, scale = 2)
    private BigDecimal subscriptionPrice;

    @Column(precision = 10, scale = 2)
    private BigDecimal oneTimePrice;

    private boolean active = true;

    public boolean isSubscription() {
        return durationInDays != null && durationInDays > 0;
    }

    public boolean isOneTimePurchase() {
        return oneTimePrice != null && oneTimePrice.compareTo(BigDecimal.ZERO) > 0;
    }
}