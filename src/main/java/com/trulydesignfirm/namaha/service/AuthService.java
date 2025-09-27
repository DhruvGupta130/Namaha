package com.trulydesignfirm.namaha.service;

import com.trulydesignfirm.namaha.dto.OtpRequest;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.dto.UserDto;
import com.trulydesignfirm.namaha.dto.VerifyOtp;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface AuthService {
    Response getOtp(@Valid OtpRequest request);

    Response verifyOtp(@Valid VerifyOtp request);

    Response createUser(@Valid UserDto userDto);

    Response getSpecialUsers(String mobile, int pageNumber, int pageSize);

    Response updateUserActiveStatus(UUID userId);

    Response getAllCustomers(int pageNumber, int pageSize, String keyword);

    Response getDashboardStats();
}