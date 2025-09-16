package com.trulydesignfirm.namaha.repository;

import com.trulydesignfirm.namaha.model.ProductVariety;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVarietyRepo extends JpaRepository<ProductVariety, String> {
}
