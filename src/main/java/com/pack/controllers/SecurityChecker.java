package com.pack.controllers;

import com.pack.entity.RolePage;
import com.pack.entity.UserRole;
import com.pack.repository.RolePageRepository;
import com.pack.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityChecker {
    private final RolePageRepository rolePageRepository;
    private final LoginService loginService;

    public RolePage check(HttpServletRequest request) {
        UserRole userRole = loginService.getCurrentUser();
        if (userRole == null)
            return new RolePage();
        Optional<RolePage> optionalRolePage = rolePageRepository.findByRoleIdAndLink(userRole.getRole().getId(), request.getRequestURI());
        RolePage page = optionalRolePage.get();
        return page;
    }

}
