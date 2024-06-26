package com.pack.controllers;

import com.pack.dto.PaperClients;
import com.pack.entity.*;
import com.pack.model.ClientSearch;
import com.pack.repository.RoleRepository;
import com.pack.service.TaskService;
import com.pack.service.TaskService.CountResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        model.addAttribute("statsFor", statsFor);

        List<Task> tasks = result.tasks();



        /*List<List<String>> elems = new ArrayList<>();
        for (Task task : tasks){
            List<String> array = new ArrayList<>();
//            String[] arr1 = {task.getUser(), };
            array.add(task.getUser());
            array.add(String.valueOf(task.getFactPayDate()));
            array.add(task.getId());
            array.add(task.getDocNumber());
            array.add(Long.toString(task.getTotalRub(), 2));
            array.add(Long.toString(task.getTaxRub(), 2));
            elems.add(array);
        }
        System.out.println(tasks);

        String[] arr2 = {"Клиент", "Дата платежа", "Заявка", "Договор", "Общая сумма", "Налог"};
        List<String> heads = new ArrayList<>(Arrays.asList(arr2));

        model.addAttribute("done_values", elems);
        model.addAttribute("done_header", heads);*/

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
