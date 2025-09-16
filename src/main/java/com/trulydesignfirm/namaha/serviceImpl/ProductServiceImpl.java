package com.trulydesignfirm.namaha.serviceImpl;

import com.trulydesignfirm.namaha.constant.ProductCategory;
import com.trulydesignfirm.namaha.dto.ProductDto;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.model.Product;
import com.trulydesignfirm.namaha.repository.ProductRepo;
import com.trulydesignfirm.namaha.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Response getAllProducts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ProductDto> productDtos = productRepo
                .findAll(pageable)
                .map(ProductDto::new);
        return new Response("All Products fetched", HttpStatus.OK, productDtos);
    }

    @Override
    public Response getFlowerStore(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ProductDto> productDtos = productRepo
                .findAllByCategory(ProductCategory.FLOWER_STORE, pageable)
                .map(ProductDto::new);
        return new Response("Flower Store Items fetched", HttpStatus.OK, productDtos);
    }

    @Override
    public Response getPoojaStore(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ProductDto> productDtos = productRepo
                .findAllByCategory(ProductCategory.POOJA_STORE, pageable)
                .map(ProductDto::new);
        return new Response("Flower Store Items fetched", HttpStatus.OK, productDtos);
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
