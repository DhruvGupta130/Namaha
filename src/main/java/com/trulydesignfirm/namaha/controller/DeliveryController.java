package com.trulydesignfirm.namaha.controller;

import com.trulydesignfirm.namaha.constant.DeliveryStatus;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.service.DeliveryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
@Tag(name = "Delivery", description = "Routes only exposed to delivery partners")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/pending")
    public ResponseEntity<Response> pending(@RequestParam int pageNumber, @RequestParam int pageSize) {
        Response allDeliveries = deliveryService.getAllPendingDeliveries(pageNumber, pageSize);
        return new ResponseEntity<>(allDeliveries, allDeliveries.status());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> update(@PathVariable UUID id, @RequestParam DeliveryStatus status) {
        Response response = deliveryService.updateDeliveryStatus(id, status);
        return new ResponseEntity<>(response, response.status());
    }
}
