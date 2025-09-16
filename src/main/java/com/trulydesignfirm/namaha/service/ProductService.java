package com.trulydesignfirm.namaha.service;

import com.trulydesignfirm.namaha.constant.DeliverySlot;
import com.trulydesignfirm.namaha.dto.ProductDto;
import com.trulydesignfirm.namaha.dto.Response;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    Response getProductVarieties();

    Response createVariety(String varietyName);

    Response deleteVariety(String varietyName);

    Response getProductCategories();

    Response createCategory(String categoryName);

    Response deleteCategory(String categoryName);

    Response createProduct(@Valid ProductDto dto);

    Response updateProduct(Long id, @Valid ProductDto dto);

    Response deleteProduct(Long id);

    Response getAllProducts(int pageNumber, int pageSize);

    Response getProductsByCategory(int pageNumber, int pageSize, String categoryName);

    Response getAllSubscriptions(String mobile, int pageNumber, int pageSize);

    Response getAllDeliverySlots();

    Response createSubscription(String mobile, Long productId, DeliverySlot slot);
}
