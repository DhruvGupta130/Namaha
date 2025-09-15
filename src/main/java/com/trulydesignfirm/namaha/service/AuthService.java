package com.trulydesignfirm.namaha.service;

import com.trulydesignfirm.namaha.dto.LoginRequest;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.dto.SignupRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    Response signup(@Valid SignupRequest request);
    Response login(@Valid LoginRequest request);
}