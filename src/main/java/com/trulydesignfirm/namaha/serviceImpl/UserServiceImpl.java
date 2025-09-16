package com.trulydesignfirm.namaha.serviceImpl;

import com.trulydesignfirm.namaha.dto.AddressDto;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.dto.UpdateUser;
import com.trulydesignfirm.namaha.dto.UserInfo;
import com.trulydesignfirm.namaha.exception.AuthException;
import com.trulydesignfirm.namaha.exception.ResourceNotFoundException;
import com.trulydesignfirm.namaha.exception.UserException;
import com.trulydesignfirm.namaha.model.Address;
import com.trulydesignfirm.namaha.model.LoginUser;
import com.trulydesignfirm.namaha.repository.AddressRepo;
import com.trulydesignfirm.namaha.repository.LoginUserRepo;
import com.trulydesignfirm.namaha.service.ServiceAreaService;
import com.trulydesignfirm.namaha.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final LoginUserRepo loginUserRepo;
    private final AddressRepo addressRepository;
    private final ServiceAreaService serviceAreaService;

    @Override
    public Response getUserInfo(String mobile) {
        UserInfo userInfo = loginUserRepo
                .findByMobile(mobile)
                .map(UserInfo::new)
                .orElseThrow(() -> new AuthException("User Not found!"));
        return new Response("User Info Retrieved Successfully", HttpStatus.OK, userInfo);
    }

    @Override
    public Response getUserAddress(String mobile) {
        AddressDto addressDto = addressRepository
                .findByUser_Mobile(mobile)
                .map(AddressDto::new)
                .orElseThrow(() -> new UserException("No Address Found!"));
        return new Response("User Address Retrieved Successfully", HttpStatus.OK, addressDto);
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

    @Override
    public Response checkServiceArea(String mobile) {
        Address address = addressRepository
                .findByUser_Mobile(mobile)
                .orElseThrow(() -> new ResourceNotFoundException("No Address Found!"));
        boolean isDeliverable = serviceAreaService.isDeliverable(address.getLatitude(), address.getLongitude());
        String message = isDeliverable ? "Area is serviceable" : "Area is not serviceable";
        return new Response(message, HttpStatus.OK, Map.of("isDeliverable", isDeliverable));
    }

    private LoginUser getUser(String mobile) {
        return loginUserRepo
                .findByMobile(mobile)
                .orElseThrow(() -> new UserException("User not found!"));
    }
}
