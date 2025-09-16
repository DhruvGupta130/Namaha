package com.trulydesignfirm.namaha.serviceImpl;

import com.trulydesignfirm.namaha.constant.DeliverySlot;
import com.trulydesignfirm.namaha.constant.SubscriptionStatus;
import com.trulydesignfirm.namaha.dto.*;
import com.trulydesignfirm.namaha.exception.ResourceNotFoundException;
import com.trulydesignfirm.namaha.exception.UserException;
import com.trulydesignfirm.namaha.model.*;
import com.trulydesignfirm.namaha.repository.*;
import com.trulydesignfirm.namaha.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final ProductVarietyRepo productVarietyRepo;
    private final ProductCategoryRepo productCategoryRepo;
    private final LoginUserRepo loginUserRepo;
    private final SubscriptionRepo subscriptionRepo;

    @Override
    public Response getProductVarieties() {
        List<ProductVarietyDto> productVarietyDtos = productVarietyRepo
                .findAll()
                .stream()
                .map(ProductVarietyDto::new)
                .toList();
        return new Response("All Product Varieties fetched", HttpStatus.OK, productVarietyDtos);
    }

    @Override
    public Response createVariety(String varietyName) {
        ProductVariety variety = new ProductVariety(varietyName);
        productVarietyRepo.save(variety);
        return new Response("Product Variety created successfully!", HttpStatus.CREATED, null);
    }

    @Override
    public Response deleteVariety(String varietyName) {
        try {
            productVarietyRepo.deleteById(varietyName.toUpperCase());
            return new Response("Product Variety deleted successfully!", HttpStatus.OK, null);
        } catch (EmptyResultDataAccessException e) {
            return new Response("Product Variety not found!", HttpStatus.NOT_FOUND, null);
        } catch (DataIntegrityViolationException e) {
            return new Response("Product Variety is in use!", HttpStatus.BAD_REQUEST, null);
        }
    }

    @Override
    public Response getProductCategories() {
        List<ProductCategoryDto> productCategoryDtos = productCategoryRepo
                .findAll()
                .stream()
                .map(ProductCategoryDto::new)
                .toList();
        return new Response("All Product Categories fetched", HttpStatus.OK, productCategoryDtos);
    }

    @Override
    public Response createCategory(String categoryName) {
        ProductCategory category = new ProductCategory(categoryName);
        productCategoryRepo.save(category);
        return new Response("Product Category created successfully!", HttpStatus.CREATED, null);
    }

    @Override
    public Response deleteCategory(String categoryName) {
        try {
            productCategoryRepo.deleteById(categoryName.toUpperCase());
            return new Response("Product Category deleted successfully!", HttpStatus.OK, null);
        } catch (EmptyResultDataAccessException e) {
            return new Response("Product Category not found!", HttpStatus.NOT_FOUND, null);
        } catch (DataIntegrityViolationException e) {
            return new Response("Product Category is in use!", HttpStatus.BAD_REQUEST, null);
        }
    }

    @Override
    public Response createProduct(ProductDto dto) {
        Product product = new Product();
        addProductDetails(dto, product);
        return new Response("Product created successfully!", HttpStatus.CREATED, null);
    }

    @Override
    public Response updateProduct(Long id, ProductDto dto) {
        Product product = productRepo
                .findByIdAndActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found!"));
        addProductDetails(dto, product);
        return new Response("Product updated successfully!", HttpStatus.OK, null);
    }

    @Override
    public Response deleteProduct(Long id) {
        return productRepo.findByIdAndActiveTrue(id)
                .map(product -> {
                    product.setActive(false);
                    productRepo.save(product);
                    return new Response("Product marked as inactive!", HttpStatus.OK, null);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    @Override
    public Response getAllProducts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ProductDto> productDtos = productRepo
                .findAllByActiveTrue(pageable)
                .map(ProductDto::new);
        return new Response("All Products fetched", HttpStatus.OK, productDtos);
    }

    @Override
    public Response getProductsByCategory(int pageNumber, int pageSize, String categoryName) {
        ProductCategory category = productCategoryRepo
                .findById(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Product Category!"));
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ProductDto> productDtos = productRepo
                .findAllByCategoryAndActiveTrue(category, pageable)
                .map(ProductDto::new);
        return new Response("Flower Store Items fetched", HttpStatus.OK, productDtos);
    }

    @Override
    public Response getAllSubscriptions(String mobile, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<SubscriptionDto> page = subscriptionRepo.findAllByUser_Mobile(mobile, pageable)
                .map(SubscriptionDto::new);
        return new Response("Subscriptions fetched successfully!", HttpStatus.OK, page);
    }

    @Override
    public Response getAllDeliverySlots() {
        List<DeliverySlotDto> slots = Arrays
                .stream(DeliverySlot.values())
                .map(DeliverySlotDto::new)
                .toList();
        return new Response("All Delivery Slots fetched Successfully!", HttpStatus.OK, slots);
    }

    @Override
    public Response createSubscription(String mobile, Long productId, DeliverySlot slot) {
        LoginUser user = loginUserRepo
                .findByMobile(mobile)
                .orElseThrow(() -> new UserException("User not found!"));
        Product product = productRepo
                .findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
        boolean alreadySubscribed = subscriptionRepo
                .existsByUserAndProductAndStatus(user, product, SubscriptionStatus.ACTIVE);
        if (alreadySubscribed) {
            return new Response("You already have an active subscription for this product.", HttpStatus.BAD_REQUEST, null);
        }
        Subscription subscription = new Subscription(user, product, SubscriptionStatus.ACTIVE, slot);
        subscriptionRepo.save(subscription);
        return new Response("Subscription created successfully!", HttpStatus.CREATED, null);
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
