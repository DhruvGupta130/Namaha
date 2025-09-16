package com.trulydesignfirm.namaha.repository;

import com.trulydesignfirm.namaha.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepo extends JpaRepository<ProductCategory, String> {
}
