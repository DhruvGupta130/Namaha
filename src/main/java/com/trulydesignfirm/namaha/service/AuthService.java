package com.trulydesignfirm.namaha.service;

import com.trulydesignfirm.namaha.dto.LoginRequest;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.dto.SignupRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    Response getOtp(@Valid SignupRequest request);
    Response signup(@Valid SignupRequest request, String otp);
    Response login(@Valid LoginRequest request);
}