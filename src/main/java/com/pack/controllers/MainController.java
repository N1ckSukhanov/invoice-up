package com.pack.controllers;

import com.pack.dto.PaperClients;
import com.pack.entity.Client;
import com.pack.entity.Contract;
import com.pack.entity.Role;
import com.pack.entity.UserRole;
import com.pack.model.ClientSearch;
import com.pack.repository.RoleRepository;
import com.pack.service.TaskService;
import com.pack.service.TaskService.CountResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final TaskService taskService;
    private final RoleRepository roleRepository;
    private final AppService appService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/apps_calendar")
    public String apps_calendar(Model model) {
        appService.setStateModel(AppState.CALENDAR, model);
        return "admin";
    }

    @GetMapping("/apps_client_dashboard")
    public String apps_client_dashboard() {
        return "client/client_dashboard";
    }

    @GetMapping("/apps_done")
    public String apps_done(
            Model model,
            @RequestParam(defaultValue = "day") String statsFor
    ) {
        appService.setStateModel(AppState.DONE, model);
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
        return "admin";
    }


    @GetMapping("/apps_task")
    public String apps_task() {
        return "client/task";
    }

    @GetMapping("/apps_roles")
    public String apps_roles(Model model) {
        appService.setStateModel(AppState.ROLES, model);
        model.addAttribute("new_user", new UserRole());
        model.addAttribute("new_role", new Role());
        model.addAttribute("roles", roleRepository.findAll());
        return "admin";
    }

    @GetMapping("/auth_login")
    public String auth_login() {
        return "auth_login";
    }

    @GetMapping("/apps_tasks")
    public String apps_tasks(Model model) {
        appService.setStateModel(AppState.TASKS, model);
        var tasks = taskService.getTasksByStatus();
        model.addAttribute("tasks", tasks);
        return "admin";
    }


    //--------------New Controllers--------------
    @GetMapping("/apps_clients")
    public String apps_clients(Model model) {
        appService.setStateModel(AppState.CLIENTS, model);
        model.addAttribute("clientSearch", new ClientSearch());
        model.addAttribute("clients", Collections.emptyList());
        return "admin";
    }


    @GetMapping("/apps_new_client")
    public String apps_new_client(Model model) {
        appService.setStateModel(AppState.NEW_CLIENT, model);
        model.addAttribute("client", new Client());
        return "admin";
    }

    @GetMapping("/apps_new_paper")
    public String apps_new_paper(Model model) {
        appService.setStateModel(AppState.NEW_PAPER, model);
        model.addAttribute("paper", new PaperClients());
        return "admin";
    }

    @GetMapping("/apps_papers")
    public String apps_papers(Model model) {
        appService.setStateModel(AppState.PAPERS, model);
        model.addAttribute("contractSearch", new Contract());
        model.addAttribute("contracts", Collections.emptyList());
        return "admin";
    }

    @GetMapping("/apps_services")
    public String apps_services(Model model) {
        appService.setStateModel(AppState.SERVICES, model);
        return "admin";
    }

}
