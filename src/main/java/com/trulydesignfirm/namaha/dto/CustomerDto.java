package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.annotation.Mobile;
import com.trulydesignfirm.namaha.constant.Role;
import com.trulydesignfirm.namaha.model.LoginUser;
import jakarta.validation.constraints.Email;

import java.util.UUID;

public record CustomerDto(
        UUID id,

        @Email
        String email,

        @Mobile
        String mobile,

        Role role,
        String name,
        boolean active,
        long orders,
        long subscriptionRevenue,
        long totalRevenue
) {
    public CustomerDto(LoginUser user, long orders, long subscriptionRevenue, long totalRevenue) {
        this(
                user.getId(),
                user.getEmail(),
                user.getMobile(),
                user.getRole(),
                user.getName(),
                user.isActive(),
                orders,
                subscriptionRevenue,
                totalRevenue
        );
    }
}
