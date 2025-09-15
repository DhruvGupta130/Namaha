package com.trulydesignfirm.namaha.model;

import com.trulydesignfirm.namaha.constant.FlowerVariety;
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
        @Index(name = "idx_product_variety", columnList = "variety")
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @ElementCollection
    @CollectionTable(
            name = "product_images",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Column(name = "image_url", nullable = false)
    @Size(min = 1, max = 3, message = "Product must have between 1 and 3 images")
    private List<String> images = new ArrayList<>();

    @Column(nullable = false)
    private Integer weightInGrams;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FlowerVariety variety;

    @Column(nullable = false)
    private Integer durationInDays = 0;

    @Column(precision = 10, scale = 2)
    private BigDecimal subscriptionPrice;

    @Column(nullable = false)
    private Boolean oneTime = false;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal oneTimePrice;

    public boolean isSubscription() {
        return durationInDays != null && durationInDays > 0;
    }

    public boolean isOneTimePurchase() {
        return oneTime != null && oneTime;
    }
}