package com.pack.controllers;

import com.pack.dto.PaperClients;
import com.pack.entity.Contract;
import com.pack.repository.ContactRepository;
import com.pack.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/contract")
@RequiredArgsConstructor
public class ContractController {
    private final ContractRepository contractRepository;
    private final ContactRepository contactRepository;

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
        System.out.println(contract);
        List<Contract> contracts = contractRepository.findAllByClientIDAndContractNumberAndContractStatusAndSigningDateAndPlanFinishDateAndUser(
                contract.getClientID(), contract.getContractNumber(), contract.getContractStatus(),
                contract.getSigningDate(), contract.getPlanFinishDate(), contract.getUser());
        System.out.println(contracts);
        model.addAttribute("contractSearch", contract);
        model.addAttribute("contracts", contracts);
        return "1apps_papers";
    }
}
