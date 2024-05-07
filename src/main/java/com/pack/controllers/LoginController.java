package com.pack.controllers;

import com.pack.entity.UserRole;
import com.pack.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final AppService appService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userRole", new UserRole());
        return "auth_login";
    }

    @PostMapping("/authorize")
    public String authorize(@ModelAttribute UserRole userRole) {
        boolean ok = loginService.login(userRole.getLogin(), userRole.getPassword());
        System.out.println(ok);
        return "index";
    }
}
