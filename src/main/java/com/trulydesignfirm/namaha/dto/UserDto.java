package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.model.LoginUser;

public record UserDto(
        String email,
        String mobile,
        String name
) {
    public UserDto(LoginUser user) {
        this(user.getEmail(), user.getMobile(), user.getName());
    }
}
