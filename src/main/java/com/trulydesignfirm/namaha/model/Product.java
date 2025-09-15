package com.trulydesignfirm.namaha.model;

import com.trulydesignfirm.namaha.constant.FlowerVariety;
import com.trulydesignfirm.namaha.constant.ProductCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(indexes = {
        @Index(name = "idx_product_category", columnList = "category"),
        @Index(name = "idx_product_variety", columnList = "variety")
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String coverImage;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal weight;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FlowerVariety variety;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    private Long duration;   // instead of Duration

    @Column(nullable = false)
    private Boolean oneTime = false; // instead of isOneTime
}
