package com.pack.controllers;

import com.pack.dto.PaperClients;
import com.pack.entity.Client;
import com.pack.entity.Contract;
import com.pack.model.ClientSearch;
import com.pack.service.TaskService;
import com.pack.service.TaskService.CountResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class MainController {

    private final TaskService taskService;

    public MainController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/apps_calendar")
    public String apps_calendar() {
        return "1apps_calendar";
    }

    @GetMapping("/apps_client_dashboard")
    public String apps_client_dashboard() {
        return "1apps_client_dashboard";
    }

    @GetMapping("/apps_done")
    public String apps_done(
            Model model,
            @RequestParam(defaultValue = "day") String statsFor
    ) {
        CountResult result = switch (statsFor) {
            case "day" -> taskService.getCompletedByDay();
            case "week" -> taskService.getCompletedByWeek();
            case "month" -> taskService.getCompletedByMonth();
            case "quarter" -> taskService.getCompletedByQuarter();
            default -> throw new IllegalStateException("Invalid identifier");
        };
        model.addAttribute("labels", result.labels());
        model.addAttribute("counts", result.counts());
        model.addAttribute("tasks", result.tasks());
        model.addAttribute("statsFor", statsFor);
        return "1apps_done";
    }


    @GetMapping("/apps_task")
    public String apps_task() {
        return "1apps_task";
    }

    @GetMapping("/apps_tasks")
    public String apps_tasks(Model model) {
        var tasks = taskService.getTasksByStatus();
        model.addAttribute("tasks", tasks);
        return "1apps_tasks";
    }


    //--------------New Controllers--------------
    @GetMapping("/apps_clients")
    public String apps_clients(Model model) {
        model.addAttribute("clientSearch", new ClientSearch());
        model.addAttribute("clients", Collections.emptyList());
        return "1apps_clients";
    }


    @GetMapping("/apps_new_client")
    public String apps_new_client(Model model) {
        model.addAttribute("client", new Client());
        return "1apps_new_client";
    }

    @GetMapping("/apps_new_paper")
    public String apps_new_paper(Model model) {
        model.addAttribute("paper", new PaperClients());
        return "1apps_new_paper";
    }

    @GetMapping("/apps_papers")
    public String apps_papers(Model model) {
        model.addAttribute("contractSearch", new Contract());
        model.addAttribute("contracts", Collections.emptyList());
        return "1apps_papers";
    }

    @GetMapping("/apps_services")
    public String apps_services() {
        return "1apps_services";
    }

}
