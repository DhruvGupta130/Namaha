package com.trulydesignfirm.namaha.serviceImpl;

import com.trulydesignfirm.namaha.dto.AddressDto;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.dto.UpdateUser;
import com.trulydesignfirm.namaha.dto.UserDto;
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

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final LoginUserRepo loginUserRepo;
    private final AddressRepo addressRepo;
    private final ServiceAreaService serviceAreaService;

    @Override
    public Response getUserInfo(String mobile) {
        UserDto userInfo = loginUserRepo
                .findByMobile(mobile)
                .map(UserDto::new)
                .orElseThrow(() -> new AuthException("User Not found!"));
        return new Response("User Info Retrieved Successfully", HttpStatus.OK, userInfo);
    }

    @Override
    public Response getUserAddress(String mobile) {
        List<AddressDto> addressDto = addressRepo
                .findByUser_MobileAndActiveTrue(mobile)
                .stream()
                .map(AddressDto::new)
                .toList();
        return new Response("User Address Retrieved Successfully", HttpStatus.OK, addressDto);
    }

    @Override
    public Response addNewAddress(String mobile, AddressDto addressDto) {
        Address address = new Address(getUser(mobile));
        updateAddressFields(addressDto, address);
        return new Response("Address Added Successfully", HttpStatus.OK, null);
    }

    @Override
    public Response updateAddress(String mobile, Long addressId, @Valid AddressDto addressDto) {
        Address address = addressRepo
                .findByIdAndUser_MobileAndActiveTrue(addressId, mobile)
                .orElseThrow(() -> new ResourceNotFoundException("No such Address Found!"));
        updateAddressFields(addressDto, address);
        return new Response("Address Updated Successfully", HttpStatus.OK, null);
    }

    @Override
    public Response deleteAddress(String mobile, Long addressId) {
        return addressRepo
                .findByIdAndUser_MobileAndActiveTrue(addressId, mobile)
                .map(address -> {
                    address.setActive(false);
                    addressRepo.save(address);
                    return new Response("Address Deleted Successfully", HttpStatus.OK, null);
                })
                .orElseThrow(() -> new ResourceNotFoundException("No Address Found!"));
    }

    @Override
    public Response updateUser(String mobile, @Valid UpdateUser request) {
        LoginUser user = getUser(mobile);
        user.setName(request.name());
        user.setEmail(request.email());
        loginUserRepo.save(user);
        return new Response("Profile Updated Successfully", HttpStatus.OK, null);
    }

    @Override
    public Response checkServiceArea(String mobile, Long addressId) {
        Address address = addressRepo
                .findByIdAndUser_MobileAndActiveTrue(addressId, mobile)
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

    private void updateAddressFields(AddressDto addressDto, Address address) {
        address.setHouse(addressDto.house());
        address.setArea(addressDto.area());
        address.setDirections(addressDto.directions());
        address.setStreet(addressDto.street());
        address.setCity(addressDto.city());
        address.setState(addressDto.state());
        address.setCountry(addressDto.country());
        address.setPinCode(addressDto.pinCode());
        address.setAddressType(addressDto.addressType());
        address.setLatitude(addressDto.latitude());
        address.setLongitude(addressDto.longitude());
        addressRepo.save(address);
    }
}
