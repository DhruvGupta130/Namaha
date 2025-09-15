package com.trulydesignfirm.namaha.serviceImpl;

import com.trulydesignfirm.namaha.configuration.JwtService;
import com.trulydesignfirm.namaha.dto.LoginRequest;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.dto.SignupRequest;
import com.trulydesignfirm.namaha.exception.AuthException;
import com.trulydesignfirm.namaha.model.LoginUser;
import com.trulydesignfirm.namaha.repository.LoginUserRepo;
import com.trulydesignfirm.namaha.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final LoginUserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final LoginUserRepo loginUserRepo;

    @Override
    public Response signup(@Valid SignupRequest request) {
        if (userRepo.existsByEmail(request.email()) || userRepo.existsByMobile(request.mobile()))
            throw new AuthException("Email or Mobile already in use!");
        LoginUser user = new LoginUser();
        user.setEmail(request.email());
        user.setMobile(request.mobile());
        user.setName(request.name());
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepo.save(user);
        return new Response("User registered successfully", HttpStatus.CREATED, Map.of());
    }

    @Override
    public Response login(@Valid LoginRequest request) {
        try {
            LoginUser user = loginUserRepo.findByMobile(request.mobile())
                    .orElseThrow(() -> new AuthException("Invalid credentials"));
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getMobile(), request.password())
            );
            String accessToken = jwtService.generateToken(user.getMobile(), user.getId(), user.getRole());
            return new Response("Login successful", HttpStatus.OK,
                    Map.of(
                            "accessToken", accessToken,
                            "role", user.getRole()
                    )
            );
        } catch (Exception e) {
            throw new AuthException("Invalid credentials", e);
        }
    }
}
