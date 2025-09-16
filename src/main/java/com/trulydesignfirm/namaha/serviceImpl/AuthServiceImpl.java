package com.trulydesignfirm.namaha.serviceImpl;

import com.trulydesignfirm.namaha.configuration.JwtService;
import com.trulydesignfirm.namaha.dto.OtpRequest;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.dto.VerifyOtp;
import com.trulydesignfirm.namaha.exception.AuthException;
import com.trulydesignfirm.namaha.model.LoginUser;
import com.trulydesignfirm.namaha.repository.LoginUserRepo;
import com.trulydesignfirm.namaha.service.AuthService;
import com.trulydesignfirm.namaha.service.OtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final LoginUserRepo userRepo;
    private final JwtService jwtService;
    private final LoginUserRepo loginUserRepo;
    private final OtpService otpService;

    @Override
    public Response getOtp(@Valid OtpRequest request) {
        int otp = otpService.generateOtp(request.mobile());
        return new Response("OTP sent successfully to %s".formatted(request.mobile()), HttpStatus.OK, Map.of("otp", otp));
    }

    @Override
    @Transactional
    public Response verifyOtp(@Valid VerifyOtp request) {
        if (!otpService.verifyOtp(request.mobile(), request.otp())) throw new AuthException("Invalid OTP!");
        LoginUser user = loginUserRepo
                .findByMobile(request.mobile())
                .orElse(userRepo.save(new LoginUser(request.mobile())));
        boolean isProfileCompleted = user.isProfileCompleted();
        String accessToken = jwtService.generateToken(user.getMobile(), user.getId(), user.getRole());
        return new Response("Verification successful", HttpStatus.OK,
                Map.of(
                        "accessToken", accessToken,
                        "role", user.getRole(),
                        "isProfileCompleted", isProfileCompleted
                )
        );
    }
}
