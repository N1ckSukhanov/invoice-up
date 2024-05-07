package com.pack.service;

import com.pack.entity.UserRole;
import com.pack.repository.UserRoleRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Data
public class LoginService {
    private final UserRoleRepository userRoleRepository;

    private UserRole currentUser;

    public boolean login(String login, String password) {
        Optional<UserRole> role = userRoleRepository.findByLoginAndPassword(login, password);
        role.ifPresent(userRole -> currentUser = userRole);
        return role.isPresent();
    }

}
