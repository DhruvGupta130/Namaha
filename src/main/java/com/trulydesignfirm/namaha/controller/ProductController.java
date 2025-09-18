package com.trulydesignfirm.namaha.controller;

import com.trulydesignfirm.namaha.constant.DeliverySlot;
import com.trulydesignfirm.namaha.constant.SubscriptionStatus;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.service.ProductService;
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

    @GetMapping("/all")
    public ResponseEntity<Response> getAllProductItems(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam String categoryName
    ) {
        Response response = productService.getProductsByCategory(pageNumber, pageSize, categoryName);
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("/subscription")
    public ResponseEntity<Response> getSubscriptions(
            Principal principal,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Response response = productService.getAllSubscriptions(principal.getName(), pageNumber, pageSize);
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("/delivery/slots")
    public ResponseEntity<Response> getDeliverySlots() {
        Response response = productService.getAllDeliverySlots();
        return new ResponseEntity<>(response, response.status());
    }

    @PostMapping("/subscription/create/{productId}")
    public ResponseEntity<Response> createNewSubscription(
            Principal principal,
            @PathVariable Long productId,
            @RequestParam("deliverySlot") DeliverySlot slot
    ) {
        Response response = productService.createSubscription(principal.getName(), productId, slot);
        return new ResponseEntity<>(response, response.status());
    }

    @PutMapping("/subscription/update/{subscriptionId}")
    public ResponseEntity<Response> updateSubscription(
            Principal principal,
            @PathVariable UUID subscriptionId,
            @RequestParam(required = false) SubscriptionStatus status,
            @RequestParam(required = false) DeliverySlot slot
    ) {
        Response response = productService.updateSubscription(principal.getName(), subscriptionId, status, slot);
        return new ResponseEntity<>(response, response.status());
    }
}
