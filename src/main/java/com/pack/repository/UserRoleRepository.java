package com.pack.repository;

import com.pack.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    UserRole findByLogin(String login);

    Optional<UserRole> findByLoginAndPassword(String login, String password);
}
