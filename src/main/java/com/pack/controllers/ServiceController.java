package com.pack.controllers;

import com.pack.entity.Service;
import com.pack.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceRepository serviceRepository;

    @GetMapping()
    public String apps_services(Model model) {
        model.addAttribute("serviceAdd", new Service());
        model.addAttribute("serviceUpdate", new Service());
        model.addAttribute("services", serviceRepository.findAll());
        return "1apps_services";
    }

    @PostMapping("/create")
    public String createClient(@ModelAttribute Service serviceAdd) {
        serviceRepository.save(serviceAdd);
        return "redirect:/services";
    }

    @PostMapping("/update")
    public String searchClient(@ModelAttribute Service serviceUpdate) {
        Service oldService = serviceRepository.findAllByName(serviceUpdate.getName()).get(0);
        oldService.setTaxPercent(serviceUpdate.getTaxPercent());
        oldService.setVatPercent(serviceUpdate.getVatPercent());
        serviceRepository.save(oldService);
        return "redirect:/services";
    }
}
