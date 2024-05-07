package com.pack.controllers;

import com.pack.entity.Currency;
import com.pack.entity.Service;
import com.pack.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceRepository serviceRepository;
    private final AppService appService;

    @GetMapping()
    public String apps_services(Model model) {
        appService.setStateModel(AppState.SERVICES, model);
        model.addAttribute("serviceAdd", new Service());
        model.addAttribute("serviceUpdate", new Service());
        List<Service> services = serviceRepository.findAll();

        List<List<String>> elems = new ArrayList<>();
        for (Service service : services){
            List<String> array = new ArrayList<>();
            array.add(service.getName());
            array.add(service.getTaxPercent());
            array.add(service.getVatPercent());
            elems.add(array);
        }
        System.out.println(services);

        List<String> heads = new ArrayList<>();
        heads.add("Сервис");
        heads.add("% налог на доходы");
        heads.add("% НДС");

        model.addAttribute("services_values", elems);
        model.addAttribute("services_header", heads);

        return "admin";
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
