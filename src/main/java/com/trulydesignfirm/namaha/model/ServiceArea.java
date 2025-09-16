package com.trulydesignfirm.namaha.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(indexes = {
        @Index(name = "idx_pincode", columnList = "pincode")
})
public class ServiceArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // e.g., "South Delhi - Lajpat Nagar "

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String pincode; // still stored for reference

    @Column(nullable = false)
    private Double latitude; // center point

    @Column(nullable = false)
    private Double longitude; // center point

    @Column(nullable = false)
    private Double radiusKm; // coverage radius in kilometers

    private Double deliveryCharge;

    private boolean active = true;
}