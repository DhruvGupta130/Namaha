package com.trulydesignfirm.namaha.controller;

import com.trulydesignfirm.namaha.dto.AddressDto;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.dto.UpdateUser;
import com.trulydesignfirm.namaha.dto.UserInfo;
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
@Tag(name = "User", description = "User related operations")
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    @Operation(summary = "Retrieve user details")
    public ResponseEntity<UserInfo> getUserInfo(Principal principal) {
        return ResponseEntity.ok(userService.getUserInfo(principal.getName()));
    }

    @PutMapping("/reset-password")
    @Operation(summary = "Reset user's password")
    public ResponseEntity<Response> resetPassword(
            Principal principal,
            @RequestParam String currentPassword,
            @RequestParam String newPassword
    ) {
        return ResponseEntity.ok(userService.resetPassword(principal.getName(), currentPassword, newPassword));
    }


    @PutMapping("/update")
    @Operation(summary = "Update user details")
    public ResponseEntity<Response> update(Principal principal, @RequestBody @Valid UpdateUser request) {
        return ResponseEntity.ok(userService.updateUser(principal.getName(), request));
    }

    @GetMapping("/address")
    @Operation(summary = "Retrieve user address")
    public ResponseEntity<AddressDto> getAddress(Principal principal) {
        return ResponseEntity.ok(userService.getUserAddress(principal.getName()));
    }

    @PutMapping("/address")
    @Operation(summary = "Update user address")
    public ResponseEntity<Response> updateAddress(Principal principal, @RequestBody @Valid AddressDto addressDto) {
        return ResponseEntity.ok(userService.updateAddress(principal.getName(), addressDto));
    }
}
