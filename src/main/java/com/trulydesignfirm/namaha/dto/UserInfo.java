package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.model.LoginUser;

public record UserInfo(
        String email,
        String mobile,
        String name
) {
    public UserInfo(LoginUser user) {
        this(user.getEmail(), user.getMobile(), user.getName());
    }
}
