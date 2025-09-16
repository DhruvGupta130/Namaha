package com.trulydesignfirm.namaha.repository;

import com.trulydesignfirm.namaha.model.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoginUserRepo extends JpaRepository<LoginUser, UUID> {
    Optional<LoginUser> findByMobile(String mobile);
}
