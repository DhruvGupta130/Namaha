package com.trulydesignfirm.namaha.repository;

import com.trulydesignfirm.namaha.constant.DeliveryStatus;
import com.trulydesignfirm.namaha.model.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryRepo extends JpaRepository<Delivery, UUID> {
    Page<Delivery> findAllByUser_MobileAndStatus(String userMobile, DeliveryStatus status, Pageable pageable);

    Page<Delivery> findAllByUser_Mobile(String mobile, Pageable pageable);

    Page<Delivery> findAllByStatus(DeliveryStatus status, Pageable pageable);

    Optional<Delivery> findByIdAndStatus(UUID id, DeliveryStatus status);
}
