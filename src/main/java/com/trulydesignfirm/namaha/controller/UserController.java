package com.trulydesignfirm.namaha.controller;

import com.trulydesignfirm.namaha.constant.OfferType;
import com.trulydesignfirm.namaha.dto.AddressDto;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.dto.UpdateUser;
import com.trulydesignfirm.namaha.service.OfferService;
import com.trulydesignfirm.namaha.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "Routes only exposed to user")
public class UserController {

    private final UserService userService;
    private final OfferService offerService;

    @GetMapping("/info")
    @Operation(summary = "Retrieve user details")
    public ResponseEntity<Response> getUserInfo(Principal principal) {
        Response response = userService.getUserInfo(principal.getName());
        return new ResponseEntity<>(response, response.status());
    }

    @PutMapping("/update")
    @Operation(summary = "Update user details")
    public ResponseEntity<Response> update(Principal principal, @RequestBody @Valid UpdateUser request) {
        Response response = userService.updateUser(principal.getName(), request);
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("/address")
    @Operation(summary = "Retrieve user addresses")
    public ResponseEntity<Response> getAddress(Principal principal) {
        Response response = userService.getUserAddress(principal.getName());
        return new ResponseEntity<>(response, response.status());
    }

    @PostMapping("/address")
    @Operation(summary = "Add new user address")
    public ResponseEntity<Response> addAddress(
            Principal principal,
            @RequestBody @Valid AddressDto addressDto
    ) {
        Response response = userService.addNewAddress(principal.getName(), addressDto);
        return new ResponseEntity<>(response, response.status());
    }

    @PutMapping("/address/{addressId}")
    @Operation(summary = "Update user address")
    public ResponseEntity<Response> updateAddress(
            Principal principal,
            @PathVariable Long addressId,
            @RequestBody @Valid AddressDto addressDto
    ) {
        Response response = userService.updateAddress(principal.getName(), addressId, addressDto);
        return new ResponseEntity<>(response, response.status());
    }

    @DeleteMapping("/address/{addressId}")
    @Operation(summary = "Delete user address")
    public ResponseEntity<Response> deleteAddress(Principal principal, @PathVariable Long addressId) {
        Response response = userService.deleteAddress(principal.getName(), addressId);
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("/service/check/{addressId}")
    @Operation(summary = "Check service status for a given address")
    public ResponseEntity<Response> checkServiceStatus(Principal principal, @PathVariable Long addressId) {
        Response response = userService.checkServiceArea(principal.getName(), addressId);
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("/offers/get/{productId}")
    public ResponseEntity<Response> getOffers(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @PathVariable long productId,
            @RequestParam OfferType type
    ) {
        Response response = offerService.getAllEligibleOffers(pageNumber, pageSize, productId, type);
        return new ResponseEntity<>(response, response.status());
    }
}
