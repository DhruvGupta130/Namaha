package com.trulydesignfirm.namaha.repository;

import com.trulydesignfirm.namaha.constant.DeliverySlot;
import com.trulydesignfirm.namaha.constant.DeliveryStatus;
import com.trulydesignfirm.namaha.model.Delivery;
import com.trulydesignfirm.namaha.model.LoginUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryRepo extends JpaRepository<Delivery, UUID> {
    Page<Delivery> findAllByUser_MobileAndStatus(String userMobile, DeliveryStatus status, Pageable pageable);

    Page<Delivery> findAllByUser_Mobile(String mobile, Pageable pageable);

    Page<Delivery> findAllByStatus(DeliveryStatus status, Pageable pageable);

    Optional<Delivery> findByIdAndStatus(UUID id, DeliveryStatus status);

    Page<Delivery> findBySlotAndStatus(DeliverySlot slot, DeliveryStatus status, Pageable pageable);

    Page<Delivery> findBySlot(DeliverySlot slot, Pageable pageable);

    Page<Delivery> findByStatus(DeliveryStatus status, Pageable pageable);

    Page<Delivery> findByProduct_titleContainingIgnoreCaseOrUser_nameContainingIgnoreCase(
            String title, String name, Pageable pageable
    );

    long countByUser(LoginUser user);

    @Query("SELECT SUM(d.finalPrice) from Delivery d WHERE d.user.id = :userId")
    Long sumFinalPriceByUser(UUID userId);

    @Query("SELECT SUM(d.finalPrice) FROM Delivery d")
    BigDecimal totalRevenue();

}
