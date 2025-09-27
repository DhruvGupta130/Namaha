package com.trulydesignfirm.namaha.repository;

import com.trulydesignfirm.namaha.constant.SubscriptionStatus;
import com.trulydesignfirm.namaha.model.Address;
import com.trulydesignfirm.namaha.model.LoginUser;
import com.trulydesignfirm.namaha.model.Product;
import com.trulydesignfirm.namaha.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription, UUID> {
    Page<Subscription> findAllByUser_Mobile(String userMobile, Pageable pageable);

    Optional<Subscription> findByUser_MobileAndId(String mobile, UUID id);

    @Query("""
       SELECT s FROM Subscription s
       WHERE s.status = :status
         AND s.endAt > CURRENT_TIMESTAMP
       """)
    List<Subscription> findAllActiveSubscriptions(@Param("status") SubscriptionStatus status);

    boolean existsByUserAndProductAndStatusAndAddress(LoginUser user, Product product, SubscriptionStatus status, Address address);

    @Query("""
       SELECT s FROM Subscription s
       WHERE s.status <> 'EXPIRED'
         AND s.endAt <= CURRENT_TIMESTAMP
       """)
    List<Subscription> findAllUnUpdatedExpiredSubscriptions();

    @Query("SELECT SUM(s.product.subscriptionPrice) FROM Subscription s where s.user.id = :userId")
    Long sumSubscriptionPriceByUser(UUID userId);

    long countByStatus(SubscriptionStatus status);

    @Query("SELECT SUM(s.product.subscriptionPrice) FROM Subscription s")
    BigDecimal subscriptionRevenue();

}
