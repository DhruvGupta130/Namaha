package com.trulydesignfirm.namaha.repository;

import com.trulydesignfirm.namaha.model.Product;
import com.trulydesignfirm.namaha.model.ProductCategory;
import com.trulydesignfirm.namaha.model.ProductVariety;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Page<Product> findAllByCategoryAndActiveTrue(ProductCategory category, Pageable pageable);

    Page<Product> findAllByActiveTrue(Pageable pageable);

    Optional<Product> findByIdAndActiveTrue(Long id);

    @Query("""
           SELECT p FROM Product p
           WHERE p.id = :id
             AND p.active = true
             AND p.durationInDays IS NOT NULL
             AND p.durationInDays > 0
           """)
    Optional<Product> findActiveSubscriptionProductById(@Param("id") Long id);


    @Query("""
           SELECT p FROM Product p
           WHERE p.id = :id
             AND p.active = true
             AND p.oneTimePrice IS NOT NULL
             AND p.oneTimePrice > 0
           """)
    Optional<Product> findActiveOneTimeOnlyProductById(@Param("id") Long id);

    boolean existsByVariety(ProductVariety variety);

    boolean existsByCategory(ProductCategory category);
}
