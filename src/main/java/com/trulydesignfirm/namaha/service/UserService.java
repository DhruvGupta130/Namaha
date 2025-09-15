package com.trulydesignfirm.namaha.service;

import com.trulydesignfirm.namaha.dto.AddressDto;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.dto.UpdateUser;
import com.trulydesignfirm.namaha.dto.UserInfo;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserInfo getUserInfo(String mobile);

    Response resetPassword(String mobile, String currentPassword, String newPassword);

    Response updateUser(String mobile, @Valid UpdateUser request);

    AddressDto getUserAddress(String mobile);

    Response updateAddress(String mobile, AddressDto addressDto);
}
