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

    @GetMapping("/address")
    @Operation(summary = "Retrieve user address")
    public ResponseEntity<AddressDto> getAddress(Principal principal) {
        return ResponseEntity.ok(userService.getUserAddress(principal.getName()));
    }

    @PutMapping("/update")
    @Operation(summary = "Update user details")
    public ResponseEntity<Response> update(Principal principal, @RequestBody @Valid UpdateUser request) {
        Response response = userService.updateUser(principal.getName(), request);
        return new ResponseEntity<>(response, response.status());
    }

    @PutMapping("/address")
    @Operation(summary = "Update user address")
    public ResponseEntity<Response> updateAddress(Principal principal, @RequestBody @Valid AddressDto addressDto) {
        Response response = userService.updateAddress(principal.getName(), addressDto);
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("/service/check")
    public ResponseEntity<Response> checkServiceStatus(Principal principal) {
        Response response = userService.checkServiceArea(principal.getName());
        return new ResponseEntity<>(response, response.status());
    }
}
