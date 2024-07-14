package com.alijas.gimhaeswim.module.user.repository;

import com.alijas.gimhaeswim.module.common.enums.ApplyStatus;
import com.alijas.gimhaeswim.module.user.entity.User;
import com.alijas.gimhaeswim.module.user.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndStatus(String username, UserStatus userStatus);

    Page<User> findAllByApplyStatusAndStatus(Pageable pageable, ApplyStatus status, UserStatus userStatus);

    Optional<User> findByIdAndStatus(Long userId, UserStatus userStatus);
}
