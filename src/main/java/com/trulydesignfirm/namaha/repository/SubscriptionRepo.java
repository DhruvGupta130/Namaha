package com.trulydesignfirm.namaha.repository;

import com.trulydesignfirm.namaha.constant.SubscriptionStatus;
import com.trulydesignfirm.namaha.model.LoginUser;
import com.trulydesignfirm.namaha.model.Product;
import com.trulydesignfirm.namaha.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription, UUID> {
    boolean existsByUserAndProductAndStatus(LoginUser user, Product product, SubscriptionStatus status);

    Page<Subscription> findAllByUser_Mobile(String userMobile, Pageable pageable);
}
