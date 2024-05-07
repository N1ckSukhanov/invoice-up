package com.pack.controllers;

import com.pack.entity.Contract;
import com.pack.entity.Currency;
import com.pack.repository.CurrencyRepository;
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
@RequestMapping("/currency")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyRepository currencyRepository;
    private final AppService appService;

    @GetMapping()
    public String apps_cyr(Model model) {
        appService.setStateModel(AppState.CYR, model);
        model.addAttribute("currency", new Currency());
        List<Currency> currencies = currencyRepository.findAllByOrderByDateDesc();

        List<List<String>> elems = new ArrayList<>();
        for (Currency currency : currencies){
            List<String> array = new ArrayList<>();
            array.add(currency.getCur());
            array.add(String.valueOf(currency.getDate()));
            array.add(currency.getCost());
            elems.add(array);
        }
        System.out.println(currencies);

        List<String> heads = new ArrayList<>();
        heads.add("Валюта");
        heads.add("Дата");
        heads.add("Курс к рублю");

        model.addAttribute("cyr_values", elems);
        model.addAttribute("cyr_header", heads);

        model.addAttribute("currencies", currencies);
        return "admin";
    }

    @PostMapping("/add")
    public String createClient(@ModelAttribute Currency currency, Model model) {
        appService.setStateModel(AppState.CYR, model);
        currencyRepository.save(currency);
        System.out.println(currency);
        return "redirect:/currency";
    }

}
