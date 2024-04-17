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

import java.util.List;

@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientRepository clientRepository;

    @PostMapping("/create")
    public String createClient(@ModelAttribute Client client) {
        clientRepository.save(client);
        System.out.println(client);
        return "redirect:/";
    }

    @PostMapping("/search")
    public String searchClient(@ModelAttribute ClientSearch clientSearch, Model model) {
        System.out.println(clientSearch);
        List<Client> clients = clientRepository.findAllByClientShortNameIgnoreCaseAndClientStatusAndClientPrSanctions(
                clientSearch.getClientShortName(), clientSearch.getClientStatus(), clientSearch.getClientPrSanctions());
        System.out.println(clients);
        model.addAttribute("clients", clients);
        return "1apps_clients";
    }

}
