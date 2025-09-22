package com.trulydesignfirm.namaha.controller;

import com.trulydesignfirm.namaha.constant.DeliverySlot;
import com.trulydesignfirm.namaha.constant.DeliveryStatus;
import com.trulydesignfirm.namaha.constant.SubscriptionStatus;
import com.trulydesignfirm.namaha.dto.DeliveryRequestDto;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.dto.SubscriptionRequestDto;
import com.trulydesignfirm.namaha.service.DeliveryService;
import com.trulydesignfirm.namaha.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final DeliveryService deliveryService;

    @GetMapping("/all")
    @Operation(summary = "Get all products")
    public ResponseEntity<Response> getAllProductItems(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam String categoryName
    ) {
        Response response = productService.getProductsByCategory(pageNumber, pageSize, categoryName);
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("/subscription")
    @Operation(summary = "Get all subscriptions")
    public ResponseEntity<Response> getSubscriptions(
            Principal principal,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Response response = productService.getAllSubscriptions(principal.getName(), pageNumber, pageSize);
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("/delivery/slots")
    @Operation(summary = "Get all delivery slots")
    public ResponseEntity<Response> getDeliverySlots() {
        Response response = productService.getAllDeliverySlots();
        return new ResponseEntity<>(response, response.status());
    }

    @PostMapping("/subscription/create/{productId}")
    @Operation(summary = "Create a new subscription")
    public ResponseEntity<Response> createNewSubscription(Principal principal, @RequestBody SubscriptionRequestDto request) {
        Response response = productService.createSubscription(principal.getName(), request);
        return new ResponseEntity<>(response, response.status());
    }

    @PutMapping("/subscription/update/{subscriptionId}")
    @Operation(summary = "Update an existing subscription")
    public ResponseEntity<Response> updateSubscription(
            Principal principal,
            @PathVariable UUID subscriptionId,
            @RequestParam(required = false) SubscriptionStatus status,
            @RequestParam(required = false) DeliverySlot slot
    ) {
        Response response = productService.updateSubscription(principal.getName(), subscriptionId, status, slot);
        return new ResponseEntity<>(response, response.status());
    }

    @PostMapping("/delivery/create")
    @Operation(summary = "Place a one time order")
    public ResponseEntity<Response> createNewDelivery(
            Principal principal,
            @RequestBody DeliveryRequestDto request
    ) {
        Response response = deliveryService.createNewDelivery(principal.getName(), request);
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("/deliveries")
    @Operation(summary = "Get all deliveries")
    public ResponseEntity<Response> getDeliveries(
            Principal principal,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) DeliveryStatus status
    ) {
        Response response = deliveryService.getUserDeliveries(principal.getName(), pageNumber, pageSize, status);
        return new ResponseEntity<>(response, response.status());
    }
}
