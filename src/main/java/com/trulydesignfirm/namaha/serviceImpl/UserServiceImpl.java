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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final LoginUserRepo loginUserRepo;
    private final AddressRepo addressRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserInfo getUserInfo(String mobile) {
        return loginUserRepo
                .findByMobile(mobile)
                .map(UserInfo::new)
                .orElseThrow(() -> new AuthException("User Not found!"));
    }

    @Override
    public Response resetPassword(String mobile, String currentPassword, String newPassword) {
        LoginUser user = loginUserRepo
                .findByMobile(mobile)
                .orElseThrow(() -> new AuthException("User Not found!"));
        if (!passwordEncoder.matches(currentPassword, user.getPassword()))
            throw new AuthException("Provided Password is incorrect!");
        user.setPassword(passwordEncoder.encode(newPassword));
        loginUserRepo.save(user);
        return new Response("Password Updated Successfully", HttpStatus.OK, null);
    }

    @Override
    @Transactional
    public Response updateUser(String mobile, @Valid UpdateUser request) {
        LoginUser user = loginUserRepo
                .findByMobile(mobile)
                .orElseThrow(() -> new AuthException("User Not found!"));
        user.setName(request.name());
        user.setEmail(request.email());
        loginUserRepo.save(user);
        return new Response("Profile Updated Successfully", HttpStatus.OK, null);
    }

    @Override
    public AddressDto getUserAddress(String mobile) {
        return addressRepository
                .findByUser_Mobile(mobile)
                .map(AddressDto::new)
                .orElseThrow(() -> new UserException("No Address Found!"));
    }

    @Override
    public Response updateAddress(String mobile, AddressDto addressDto) {
        LoginUser user = loginUserRepo
                .findByMobile(mobile)
                .orElseThrow(() -> new UserException("User not found!"));
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
}
