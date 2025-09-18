package com.trulydesignfirm.namaha.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
@Tag(name = "Delivery", description = "Routes only exposed to delivery partners")
public class DeliveryController {

    @GetMapping("/pending")
    public String pending() {
        return "pending";
    }
}
