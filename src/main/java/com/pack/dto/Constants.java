package com.pack.dto;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Constants {
    public static final String HOME = "redirect:/";

    public String previous(HttpServletRequest request) {
        System.out.println(request.getHeader("Referer"));
        return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl).orElse(HOME);
    }
}
