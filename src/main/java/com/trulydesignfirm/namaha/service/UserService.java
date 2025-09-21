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

    Response addNewAddress(String mobile, @Valid AddressDto addressDto);

    Response updateAddress(String mobile, Long addressId, @Valid AddressDto addressDto);

    Response deleteAddress(String mobile, Long addressId);

    Response updateUser(String mobile, @Valid UpdateUser request);

    Response checkServiceArea(String mobile, Long addressId);
}
