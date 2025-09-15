package com.trulydesignfirm.namaha.controller;

import com.trulydesignfirm.namaha.dto.LoginRequest;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.dto.SignupRequest;
import com.trulydesignfirm.namaha.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication related operations")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/get-otp")
    @Operation(summary = "Get OTP for signup")
    public ResponseEntity<Response> getOtp(@RequestBody @Valid SignupRequest request) {
        Response response = authService.getOtp(request);
        return new ResponseEntity<>(response, response.status());
    }

    @PostMapping("/signup")
    @Operation(summary = "Signup a new user")
    public ResponseEntity<Response> signup(
            @RequestBody @Valid SignupRequest request,
            @RequestParam String otp
    ) {
        Response response = authService.signup(request, otp);
        return new ResponseEntity<>(response, response.status());
    }

    @PostMapping("/login")
    @Operation(summary = "Login an existing user")
    public ResponseEntity<Response> login(@RequestBody @Valid LoginRequest request) {
        Response response = authService.login(request);
        return new ResponseEntity<>(response, response.status());
    }
}
