package com.trulydesignfirm.namaha.repository;

import com.trulydesignfirm.namaha.model.Product;
import com.trulydesignfirm.namaha.model.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Page<Product> findAllByCategoryAndActiveTrue(ProductCategory category, Pageable pageable);

    Page<Product> findAllByActiveTrue(Pageable pageable);

    Optional<Product> findByIdAndActiveTrue(Long id);
}
