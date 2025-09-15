package com.trulydesignfirm.namaha.repository;

import com.trulydesignfirm.namaha.model.Address;
import com.trulydesignfirm.namaha.model.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepo extends JpaRepository<Address, Long> {
    Optional<Address> findByUser_Mobile(String userMobile);

    Optional<Address> findByUser(LoginUser user);
}