package com.pack.controllers;

import com.pack.entity.Client;
import com.pack.model.ClientSearch;
import com.pack.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientRepository clientRepository;
    private final AppService appService;

    @PostMapping("/create")
    public String createClient(@ModelAttribute Client client) {
        clientRepository.save(client);
        System.out.println(client);
        return "redirect:/";
    }

    @PostMapping("/search")
    public String searchClient(@ModelAttribute ClientSearch clientSearch, Model model) {
        appService.setStateModel(AppState.CLIENTS, model);
        System.out.println(clientSearch);
        List<Client> clients = clientRepository.findAllByClientShortNameIgnoreCaseAndClientStatusAndClientPrSanctions(
                clientSearch.getClientShortName(), clientSearch.getClientStatus(), clientSearch.getClientPrSanctions());

        List<List<String>> elems = new ArrayList<>();
        for (Client client : clients){
            List<String> array = new ArrayList<>();
            array.add(client.getClientID());
            array.add(client.getClientShortName());
            array.add(client.getClientPrSanctions());
            array.add(client.getClientPrSpecialCalendar());
            array.add(client.getClientStatus());
            elems.add(array);
        }
        System.out.println(clients);

        List<String> heads = new ArrayList<>();
        heads.add("Клиент ID");
        heads.add("Краткое наименование");
        heads.add("Признак санкций");
        heads.add("Признак особого календаря");
        heads.add("Статус");

        model.addAttribute("clients_values", elems);
        model.addAttribute("clients_header", heads);
        return "admin";
    }

}
