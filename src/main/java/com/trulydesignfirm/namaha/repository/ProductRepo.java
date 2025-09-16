package com.trulydesignfirm.namaha.repository;

import com.trulydesignfirm.namaha.constant.ProductCategory;
import com.trulydesignfirm.namaha.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Page<Product> findAllByCategory(ProductCategory category, Pageable pageable);
}
