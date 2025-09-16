package com.trulydesignfirm.namaha.service;

import com.trulydesignfirm.namaha.dto.AddressDto;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.dto.UpdateUser;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Response getUserInfo(String mobile);

    Response getUserAddress(String mobile);

    Response updateUser(String mobile, @Valid UpdateUser request);

    Response updateAddress(String mobile, AddressDto addressDto);

    Response checkServiceArea(String mobile);
}
