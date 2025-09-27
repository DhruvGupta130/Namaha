package com.trulydesignfirm.namaha.controller;

import com.trulydesignfirm.namaha.constant.DeliverySlot;
import com.trulydesignfirm.namaha.constant.DeliveryStatus;
import com.trulydesignfirm.namaha.dto.*;
import com.trulydesignfirm.namaha.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Route only exposed to admin")
public class AdminController {

    private final ProductService productService;
    private final ServiceAreaService serviceAreaService;
    private final AuthService authService;
    private final DeliveryService deliveryService;
    private final OfferService offerService;

    @GetMapping("/user/get")
    public ResponseEntity<Response> getUsers(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            Principal principal
    ) {
        Response response = authService.getSpecialUsers(principal.getName(), pageNumber, pageSize);
        return new ResponseEntity<>(response, response.status());
    }

    @PostMapping("/user/create")
    public ResponseEntity<Response> createUser(@Valid @RequestBody UserDto userDto) {
        Response response = authService.createUser(userDto);
        return new ResponseEntity<>(response, response.status());
    }

    @PatchMapping("/user/update/{userId}")
    public ResponseEntity<Response> updateUser(@PathVariable UUID userId) {
        Response response = authService.updateUserActiveStatus(userId);
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("/user/customer")
    public ResponseEntity<Response> getCustomers(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String keyword
    ) {
        Response response = authService.getAllCustomers(pageNumber, pageSize, keyword);
        return new ResponseEntity<>(response, response.status());
    }

    @PostMapping("/product/variety")
    @Operation(summary = "Create a new product variety")
    public ResponseEntity<Response> createVariety(@RequestParam("variety") String varietyName) {
        Response response = productService.createVariety(varietyName);
        return new ResponseEntity<>(response, response.status());
    }

    @DeleteMapping("/product/variety")
    @Operation(summary = "Delete a product variety")
    public ResponseEntity<Response> deleteVariety(@RequestParam("variety") String varietyName) {
        Response response = productService.deleteVariety(varietyName);
        return new ResponseEntity<>(response, response.status());
    }

    @PostMapping("/product/category")
    @Operation(summary = "Create a new product category")
    public ResponseEntity<Response> createCategory(@RequestParam String category) {
        Response response = productService.createCategory(category);
        return new ResponseEntity<>(response, response.status());
    }

    @DeleteMapping("/product/category")
    @Operation(summary = "Delete a product category")
    public ResponseEntity<Response> deleteCategory(@RequestParam("category") String categoryName) {
        Response response = productService.deleteCategory(categoryName);
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("/product/all")
    @Operation(summary = "Get all products")
    public ResponseEntity<Response> getAllProducts(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Response response = productService.getAllProducts(pageNumber, pageSize);
        return new ResponseEntity<>(response, response.status());
    }

    @PostMapping("/product/create")
    @Operation(summary = "Create a new product")
    public ResponseEntity<Response> createProduct(@RequestBody @Valid ProductDto dto) {
        Response response = productService.createProduct(dto);
        return new ResponseEntity<>(response, response.status());
    }

    @PutMapping("/product/update/{id}")
    @Operation(summary = "Update an existing product")
    public ResponseEntity<Response> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDto dto) {
        Response response = productService.updateProduct(id, dto);
        return new ResponseEntity<>(response, response.status());
    }

    @DeleteMapping("product/delete/{id}")
    @Operation(summary = "Delete a product by ID")
    public ResponseEntity<Response> deleteProduct(@PathVariable Long id) {
        Response response = productService.deleteProduct(id);
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("/service/areas")
    @Operation(summary = "Get all service areas")
    public ResponseEntity<Response> getAllServiceAreas(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Response response = serviceAreaService.getServiceAreas(pageNumber, pageSize);
        return new ResponseEntity<>(response, response.status());
    }

    @PostMapping("/service/areas")
    @Operation(summary = "Create a new service area")
    public ResponseEntity<Response> createServiceArea(@RequestBody @Valid ServiceAreaDto request) {
        Response response = serviceAreaService.createServiceArea(request);
        return new ResponseEntity<>(response, response.status());
    }

    @PutMapping("/service/areas/{id}")
    @Operation(summary = "Update an existing service area")
    public ResponseEntity<Response> updateServiceArea(@PathVariable Long id, @RequestBody @Valid ServiceAreaDto request) {
        Response response = serviceAreaService.updateServiceArea(id, request);
        return new ResponseEntity<>(response, response.status());
    }

    @PatchMapping("/service/areas/{id}")
    @Operation(summary = "Update the availability status of a service area")
    public ResponseEntity<Response> updateServiceAreaStatus(@PathVariable Long id) {
        Response response = serviceAreaService.updateServiceAvailabilityStatus(id);
        return new ResponseEntity<>(response, response.status());
    }

    @DeleteMapping("/service/areas/{id}")
    @Operation(summary = "Delete a service area by ID")
    public ResponseEntity<Response> deleteServiceArea(@PathVariable Long id) {
        Response response = serviceAreaService.deleteServiceArea(id);
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("/subscriptions")
    @Operation(summary = "Get all subscriptions")
    public ResponseEntity<Response> getSubscriptions(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Response response = productService.getAllSubscriptions(pageNumber, pageSize);
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("/deliveries")
    public ResponseEntity<Response> getAllDeliveries(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) DeliverySlot slot,
            @RequestParam(required = false) DeliveryStatus status,
            @RequestParam(required = false) String keyword
    ) {
        Response response = deliveryService.getAllDeliveries(pageNumber, pageSize, slot, status, keyword);
        return new ResponseEntity<>(response, response.status());
    }

    @PatchMapping("/delivery/status/{id}")
    public ResponseEntity<Response> updateDeliveryStatus(@PathVariable UUID id, @RequestParam DeliveryStatus status) {
        Response response = deliveryService.updateDeliveryStatus(id, status);
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("/offer/get")
    public ResponseEntity<Response> getAllOffers(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false ) String keyword
    ) {
        Response response = offerService.getAllOffers(pageNumber, pageSize, keyword);
        return new ResponseEntity<>(response, response.status());
    }

    @PostMapping("/offer/create")
    public ResponseEntity<Response> createOffer(@RequestBody @Valid OfferCreateDto request) {
        Response response = offerService.createNewOffer(request);
        return new ResponseEntity<>(response, response.status());
    }

    @PatchMapping("/offer/status/{offerId}")
    public ResponseEntity<Response> updateOfferStatus(@PathVariable UUID offerId) {
        Response response = offerService.updateOfferStatus(offerId);
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("/dashboard/stats")
    public ResponseEntity<Response> getDashboardStats() {
        Response response = authService.getDashboardStats();
        return new ResponseEntity<>(response, response.status());
    }

}
