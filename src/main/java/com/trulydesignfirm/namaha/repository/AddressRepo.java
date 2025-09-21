package com.trulydesignfirm.namaha.repository;

import com.trulydesignfirm.namaha.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepo extends JpaRepository<Address, Long> {
    List<Address> findByUser_MobileAndActiveTrue(String userMobile);
    Optional<Address> findByIdAndUser_MobileAndActiveTrue(Long addressId, String mobile);
}