package com.trulydesignfirm.namaha.serviceImpl;

import com.trulydesignfirm.namaha.dto.ProductDto;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.model.Product;
import com.trulydesignfirm.namaha.repository.ProductRepo;
import com.trulydesignfirm.namaha.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    @Override
    public Response createProduct(ProductDto dto) {
        Product product = new Product();
        addProductDetails(dto, product);
        return new Response("Product created successfully!", HttpStatus.CREATED, null);
    }

    @Override
    public Response updateProduct(Long id, ProductDto dto) {
        Product product = productRepo
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found!"));
        addProductDetails(dto, product);
        return new Response("Product updated successfully!", HttpStatus.OK, null);
    }

    @Override
    public Response deleteProduct(Long id) {
        return new Response("Product deleted successfully!", HttpStatus.OK, null);
    }

    private void addProductDetails(ProductDto dto, Product product) {
        product.setTitle(dto.title());
        product.setDescription(dto.description());
        product.setImages(dto.images());
        product.setWeightInGrams(dto.weightInGrams());
        product.setVariety(dto.variety());
        product.setDurationInDays(dto.durationInDays());
        product.setSubscriptionPrice(dto.subscriptionPrice());
        product.setOneTime(dto.oneTime());
        product.setOneTimePrice(dto.oneTimePrice());
        productRepo.save(product);
    }
}
