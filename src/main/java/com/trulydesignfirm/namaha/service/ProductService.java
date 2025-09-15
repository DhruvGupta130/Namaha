package com.trulydesignfirm.namaha.service;

import com.trulydesignfirm.namaha.dto.ProductDto;
import com.trulydesignfirm.namaha.dto.Response;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    Response createProduct(@Valid ProductDto dto);

    Response updateProduct(Long id, @Valid ProductDto dto);

    Response deleteProduct(Long id);
}
