package com.trulydesignfirm.namaha.serviceImpl;

import com.trulydesignfirm.namaha.configuration.JwtService;
import com.trulydesignfirm.namaha.constant.Role;
import com.trulydesignfirm.namaha.constant.SubscriptionStatus;
import com.trulydesignfirm.namaha.dto.*;
import com.trulydesignfirm.namaha.exception.AuthException;
import com.trulydesignfirm.namaha.model.LoginUser;
import com.trulydesignfirm.namaha.repository.DeliveryRepo;
import com.trulydesignfirm.namaha.repository.LoginUserRepo;
import com.trulydesignfirm.namaha.repository.SubscriptionRepo;
import com.trulydesignfirm.namaha.service.AuthService;
import com.trulydesignfirm.namaha.service.OtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final LoginUserRepo loginUserRepo;
    private final OtpService otpService;
    private final DeliveryRepo deliveryRepo;
    private final SubscriptionRepo subscriptionRepo;

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
                .orElseGet(() -> loginUserRepo.save(new LoginUser(request.mobile())));
        if (!user.isActive()) throw new AuthException("Account has been disabled!");
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

    @Override
    public Response createUser(@Valid UserDto userDto) {
        LoginUser user = loginUserRepo
                .findByMobile(userDto.mobile())
                .orElseGet(() -> loginUserRepo.save(new LoginUser(userDto.mobile())));
        user.setRole(userDto.role());
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        loginUserRepo.save(user);
        return new Response("User updated successfully", HttpStatus.OK, null);
    }

    @Override
    public Response getSpecialUsers(String mobile, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Role> roles = List.of(Role.ADMIN, Role.DELIVERY);
        Page<UserDto> users = loginUserRepo
                .findAllByRoleInAndMobileNot(roles, mobile, pageable)
                .map(UserDto::new);
        return new Response("Users fetched successfully", HttpStatus.OK, users);
    }

    @Override
    public Response updateUserActiveStatus(UUID userId) {
        LoginUser user = loginUserRepo
                .findById(userId)
                .orElseThrow(() -> new AuthException("User not found!"));
        user.setActive(!user.isActive());
        loginUserRepo.save(user);
        return new Response("User account status updated successfully", HttpStatus.OK, Map.of("user", userId));
    }

    @Override
    public Response getAllCustomers(int pageNumber, int pageSize, String keyword) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<CustomerDto> customers;

        if (keyword == null || keyword.isBlank()) {
            customers = loginUserRepo.findAllByRole(Role.USER, pageable)
                    .map(user -> new CustomerDto(
                            user,
                            getOrderCount(user),         // implement this
                            getSubscriptionRevenue(user),// implement this
                            getTotalRevenue(user)        // implement this
                    ));
        } else {
            customers = loginUserRepo
                    .findByRoleAndNameContainingIgnoreCaseOrRoleAndEmailContainingIgnoreCaseOrRoleAndMobileContainingIgnoreCase(
                            Role.USER, keyword,
                            Role.USER, keyword,
                            Role.USER, keyword,
                            pageable
                    ).map(user -> new CustomerDto(
                            user,
                            getOrderCount(user),
                            getSubscriptionRevenue(user),
                            getTotalRevenue(user)
                    ));
        }

        return new Response("Customers fetched successfully", HttpStatus.OK, customers);
    }

    @Override
    public Response getDashboardStats() {
        long totalUsers = loginUserRepo.countByRole(Role.USER);
        long activeUsers = loginUserRepo.countByRoleAndActiveTrue(Role.USER);

        long totalSubscriptions = subscriptionRepo.count();
        long activeSubscriptions = subscriptionRepo.countByStatus(SubscriptionStatus.ACTIVE);

        BigDecimal totalRevenue = deliveryRepo.totalRevenue(); // custom query in repo
        BigDecimal subscriptionRevenue = subscriptionRepo.subscriptionRevenue(); // optional

        Map<String, Object> stats = Map.of(
                "totalUsers", totalUsers,
                "activeUsers", activeUsers,
                "totalSubscriptions", totalSubscriptions,
                "activeSubscriptions", activeSubscriptions,
                "totalRevenue", totalRevenue,
                "subscriptionRevenue", subscriptionRevenue
        );

        return new Response("Dashboard stats fetched successfully", HttpStatus.OK, stats);
    }


    private long getOrderCount(LoginUser user) {
        return deliveryRepo.countByUser(user);
    }

    private long getSubscriptionRevenue(LoginUser user) {
        Long revenue = subscriptionRepo.sumSubscriptionPriceByUser(user.getId());
        return revenue != null ? revenue : 0L;
    }

    private long getTotalRevenue(LoginUser user) {
        Long orderRevenue = deliveryRepo.sumFinalPriceByUser(user.getId());
        return orderRevenue != null ? orderRevenue : 0L;
    }

}
