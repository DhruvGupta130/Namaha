package com.trulydesignfirm.namaha.repository;

import com.trulydesignfirm.namaha.constant.Role;
import com.trulydesignfirm.namaha.model.LoginUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoginUserRepo extends JpaRepository<LoginUser, UUID> {
    Optional<LoginUser> findByMobile(String mobile);

    Page<LoginUser> findAllByRoleInAndMobileNot(Collection<Role> roles, String mobile, Pageable pageable);

    Page<LoginUser> findAllByRole(Role role, Pageable pageable);

    Page<LoginUser> findByRoleAndNameContainingIgnoreCaseOrRoleAndEmailContainingIgnoreCaseOrRoleAndMobileContainingIgnoreCase(
            Role role1, String name,
            Role role2, String email,
            Role role3, String mobile,
            Pageable pageable
    );

    long countByRole(Role role);

    long countByRoleAndActiveTrue(Role role);
}
