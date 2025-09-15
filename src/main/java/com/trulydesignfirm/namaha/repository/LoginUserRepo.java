package com.trulydesignfirm.namaha.repository;

import com.trulydesignfirm.namaha.model.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoginUserRepo extends JpaRepository<LoginUser, UUID> {
    Optional<LoginUser> findByMobile(String mobile);

    @Query("SELECT u FROM LoginUser u WHERE u.email = :username OR u.mobile = :username")
    Optional<LoginUser> findByUsername(@Param("mobile") String username);

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);
}
