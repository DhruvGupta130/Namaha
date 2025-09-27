package com.trulydesignfirm.namaha.repository;

import com.trulydesignfirm.namaha.constant.OfferType;
import com.trulydesignfirm.namaha.model.Offers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OffersRepo extends JpaRepository<Offers, UUID> {
    Optional<Offers> findByCouponCodeAndActiveTrue(String couponCode);

    Page<Offers> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description, Pageable pageable);

    @Query("SELECT o FROM Offers o " +
            "WHERE o.offerType = :type " +
            "AND o.active = true " +
            "AND o.minOrderAmount <= :price " +
            "ORDER BY o.createdAt DESC")
    Page<Offers> findEligibleOffers(
            @Param("type") OfferType type,
            @Param("price") BigDecimal price,
            Pageable pageable
    );
}