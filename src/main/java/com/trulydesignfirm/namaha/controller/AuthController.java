package com.trulydesignfirm.namaha.controller;

import com.trulydesignfirm.namaha.dto.OtpRequest;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.dto.VerifyOtp;
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

    @PostMapping("/send-otp")
    @Operation(summary = "Step1 -> Get OTP for login/verifyOtp")
    public ResponseEntity<Response> getOtp(@RequestBody @Valid OtpRequest request) {
        Response response = authService.getOtp(request);
        return new ResponseEntity<>(response, response.status());
    }

    @PostMapping("/verify-otp")
    @Operation(summary = "Step2 -> Login/verifyOtp a user")
    public ResponseEntity<Response> verifyOtp(@RequestBody @Valid VerifyOtp request) {
        Response response = authService.verifyOtp(request);
        return new ResponseEntity<>(response, response.status());
    }

}
