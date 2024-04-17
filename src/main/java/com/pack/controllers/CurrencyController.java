package com.pack.controllers;

import com.pack.dto.PaperClients;
import com.pack.entity.Currency;
import com.pack.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/currency")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyRepository currencyRepository;

    @GetMapping()
    public String apps_cyr(Model model) {
        model.addAttribute("currency", new Currency());
        List<Currency> currencies = currencyRepository.findAllByOrderByDateDesc();
        model.addAttribute("currencies", currencies);
        return "1apps_cyr";
    }

    @PostMapping("/add")
    public String createClient(@ModelAttribute Currency currency) {
        currencyRepository.save(currency);
        System.out.println(currency);
        return "redirect:/currency";
    }

}
