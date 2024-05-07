package com.pack.repository;

import com.pack.entity.RolePage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RolePageRepository extends JpaRepository<RolePage, Integer> {
    List<RolePage> findAllByRoleId(Integer roleId);

    Optional<RolePage> findByRoleIdAndLink(Integer roleId, String link);

    Optional<RolePage> findByNameAndRoleId(String name, Integer roleId);
}
