package com.trulydesignfirm.namaha.serviceImpl;

import com.trulydesignfirm.namaha.constant.DeliverySlot;
import com.trulydesignfirm.namaha.constant.SubscriptionStatus;
import com.trulydesignfirm.namaha.dto.*;
import com.trulydesignfirm.namaha.exception.ProductException;
import com.trulydesignfirm.namaha.exception.ResourceNotFoundException;
import com.trulydesignfirm.namaha.exception.SubscriptionException;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
        List<String> productVarieties = productVarietyRepo
                .findAll()
                .stream()
                .map(ProductVariety::getName)
                .toList();
        return new Response("All Product Varieties fetched", HttpStatus.OK, productVarieties);
    }

    @Override
    public Response createVariety(String varietyName) {
        ProductVariety variety = new ProductVariety(varietyName);
        productVarietyRepo.save(variety);
        return new Response("Product Variety created successfully!", HttpStatus.CREATED, null);
    }

    @Override
    @Transactional
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
        List<String> productCategories = productCategoryRepo
                .findAll()
                .stream()
                .map(ProductCategory::getName)
                .toList();
        return new Response("All Product Categories fetched", HttpStatus.OK, productCategories);
    }

    @Override
    public Response createCategory(String categoryName) {
        ProductCategory category = new ProductCategory(categoryName);
        productCategoryRepo.save(category);
        return new Response("Product Category created successfully!", HttpStatus.CREATED, null);
    }

    @Override
    @Transactional
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
    @Transactional
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

    @Override
    public Response updateSubscription(String mobile, UUID subscriptionId, SubscriptionStatus status, DeliverySlot slot) {
        Subscription subscription = subscriptionRepo
                .findByUser_MobileAndId(mobile, subscriptionId)
                .orElseThrow(() -> new SubscriptionException("Subscription not found!"));
        if (!subscription.getStatus().isUpdatable()) {
            throw new SubscriptionException("Subscription cannot be updated! Create a new subscription instead");
        }
        if (status == null && slot == null) {
            throw new SubscriptionException("Nothing to update! Provide status or slot.");
        }
        if (status != null) subscription.setStatus(status);
        if (slot != null) subscription.setDeliverySlot(slot);
        subscriptionRepo.save(subscription);
        return new Response("Subscription updated successfully!", HttpStatus.OK, null);
    }

    private void addProductDetails(ProductDto dto, Product product) {
        boolean hasValidSubscription =
                dto.durationInDays() != null && dto.durationInDays() > 0 &&
                        dto.subscriptionPrice() != null && dto.subscriptionPrice().compareTo(BigDecimal.ZERO) > 0;
        boolean hasValidOneTime =
                dto.oneTimePrice() != null && dto.oneTimePrice().compareTo(BigDecimal.ZERO) > 0;

        // ✅ Validation rule: at least one option must be valid
        if (!hasValidSubscription && !hasValidOneTime) {
            throw new ProductException(
                    "Product must be either a valid subscription or a valid one-time purchase (or both)."
            );
        }
        product.setTitle(dto.title());
        product.setDescription(dto.description());
        product.setImages(dto.images());
        product.setWeightInGrams(dto.weightInGrams());
        product.setVariety(new ProductVariety(dto.variety()));
        product.setCategory(new ProductCategory(dto.category()));

        // ---- Set subscription details ----
        if (hasValidSubscription) {
            product.setDurationInDays(dto.durationInDays());
            product.setSubscriptionPrice(dto.subscriptionPrice());
        } else {
            product.setDurationInDays(null);
            product.setSubscriptionPrice(null);
        }

        // ---- Set one-time details ----
        product.setOneTimePrice(hasValidOneTime ? dto.oneTimePrice() : null);
        productRepo.save(product);
    }
}
