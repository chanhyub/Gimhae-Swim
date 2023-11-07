package com.alijas.gimhaeswim.module.user.repository;

import com.alijas.gimhaeswim.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
