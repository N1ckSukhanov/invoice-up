package com.pack.controllers;

import com.pack.dto.PaperClients;
import com.pack.entity.Client;
import com.pack.entity.Contract;
import com.pack.repository.ContactRepository;
import com.pack.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/contract")
@RequiredArgsConstructor
public class ContractController {
    private final ContractRepository contractRepository;
    private final ContactRepository contactRepository;
    private final AppService appService;

    @PostMapping("/create")
    public String createClient(@ModelAttribute PaperClients paperClients) {
        contractRepository.save(paperClients.getContract());
        contactRepository.save(paperClients.getContact1());
        contactRepository.save(paperClients.getContact2());
        System.out.println(paperClients);
        return "redirect:/";
    }

    @PostMapping("/search")
    public String searchClient(@ModelAttribute Contract contract, Model model) {
        appService.setStateModel(AppState.PAPERS, model);
        System.out.println(contract);
        List<Contract> contracts = contractRepository.findAllByClientIDAndContractNumberAndContractStatusAndSigningDateAndPlanFinishDateAndUser(
                contract.getClientID(), contract.getContractNumber(), contract.getContractStatus(),
                contract.getSigningDate(), contract.getPlanFinishDate(), contract.getUser());
        System.out.println(contracts);

        List<List<String>> elems = new ArrayList<>();
        for (Contract contract1 : contracts){
            List<String> array = new ArrayList<>();
            array.add(contract1.getClientID());
            array.add(contract1.getContractNumber());
            array.add(contract1.getSigningDate());
            array.add(contract1.getPlanFinishDate());
            array.add(contract1.getUser());
            array.add(contract1.getContractStatus());
            elems.add(array);
        }
        System.out.println(contracts);

        List<String> heads = new ArrayList<>();
        heads.add("Клиент");
        heads.add("Номер договора");
        heads.add("Дата подписания");
        heads.add("Дата расторжения");
        heads.add("Уплата налога");
        heads.add("Статус");

        model.addAttribute("papers_values", elems);
        model.addAttribute("papers_header", heads);

        model.addAttribute("contractSearch", contract);
        model.addAttribute("contracts", contracts);
        return "admin";
    }
}
