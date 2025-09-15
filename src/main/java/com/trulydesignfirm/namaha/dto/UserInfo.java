package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.model.LoginUser;

import java.util.UUID;

public record UserInfo(
        UUID id,
        String email,
        String mobile,
        String name
) {
    public UserInfo(LoginUser user) {
        this(user.getId(), user.getEmail(), user.getMobile(), user.getName());
    }
}
