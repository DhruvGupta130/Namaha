package com.trulydesignfirm.namaha.serviceImpl;

import com.trulydesignfirm.namaha.dto.AddressDto;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.dto.UpdateUser;
import com.trulydesignfirm.namaha.dto.UserInfo;
import com.trulydesignfirm.namaha.exception.AuthException;
import com.trulydesignfirm.namaha.exception.UserException;
import com.trulydesignfirm.namaha.model.Address;
import com.trulydesignfirm.namaha.model.LoginUser;
import com.trulydesignfirm.namaha.repository.AddressRepo;
import com.trulydesignfirm.namaha.repository.LoginUserRepo;
import com.trulydesignfirm.namaha.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final LoginUserRepo loginUserRepo;
    private final AddressRepo addressRepository;

    @Override
    public UserInfo getUserInfo(String mobile) {
        return loginUserRepo
                .findByMobile(mobile)
                .map(UserInfo::new)
                .orElseThrow(() -> new AuthException("User Not found!"));
    }

    @Override
    public AddressDto getUserAddress(String mobile) {
        return addressRepository
                .findByUser_Mobile(mobile)
                .map(AddressDto::new)
                .orElseThrow(() -> new UserException("No Address Found!"));
    }

    @Override
    @Transactional
    public Response updateUser(String mobile, @Valid UpdateUser request) {
        LoginUser user = getUser(mobile);
        user.setName(request.name());
        user.setEmail(request.email());
        loginUserRepo.save(user);
        return new Response("Profile Updated Successfully", HttpStatus.OK, null);
    }

    @Override
    public Response updateAddress(String mobile, AddressDto addressDto) {
        LoginUser user = getUser(mobile);
        Address address = addressRepository
                .findByUser(user)
                .orElse(new Address(user));
        address.setStreet(addressDto.street());
        address.setCity(addressDto.city());
        address.setState(addressDto.state());
        address.setCountry(addressDto.country());
        address.setPinCode(addressDto.pinCode());
        address.setLatitude(addressDto.latitude());
        address.setLongitude(addressDto.longitude());
        addressRepository.save(address);
        return new Response("Address Updated Successfully", HttpStatus.OK, null);
    }

    private LoginUser getUser(String mobile) {
        return loginUserRepo
                .findByMobile(mobile)
                .orElseThrow(() -> new UserException("User not found!"));
    }
}
