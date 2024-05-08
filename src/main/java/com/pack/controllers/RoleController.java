package com.pack.controllers;

import com.pack.dto.Constants;
import com.pack.entity.Client;
import com.pack.entity.Role;
import com.pack.entity.RolePage;
import com.pack.entity.UserRole;
import com.pack.repository.RolePageRepository;
import com.pack.repository.RoleRepository;
import com.pack.repository.UserRoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final RolePageRepository rolePageRepository;
    private final SecurityChecker securityChecker;
    private final Constants constants;
    private final Integer initRoleId = 1;
    private Integer chosenRoleId = null;
    private final AppService appService;

    private Role saveRole() {
        Role role = new Role();
        roleRepository.save(role);
        roleRepository.flush();
        return role;
    }

    @GetMapping()
    public String roles(Model model,
                        HttpServletRequest request) {
        appService.setStateModel(AppState.ROLES, model);
        RolePage rolePage = securityChecker.check(request);
//        if (!rolePage.isRead())
//            return constants.previous(request);
        model.addAttribute("new_user", new UserRole()); //  /role/create_user
        model.addAttribute("new_role", new Role()); //  /role/create
        model.addAttribute("can_write", rolePage.isWrite());

        model.addAttribute("choose_role", new Role()); //  /role/choose
        model.addAttribute("roles", roleRepository.findAll()); //  /role/create_user

        List<UserRole> userRoles = userRoleRepository.findAll();
        model.addAttribute("user_roles", userRoleRepository.findAll()); //  /role
        model.addAttribute("pages_create", rolePageRepository.findAllByRoleId(initRoleId));
        model.addAttribute("find_role", new Role());
        System.out.println(chosenRoleId);
        if (chosenRoleId != null) { //  /role/change
            System.out.println(rolePageRepository.findAllByRoleId(chosenRoleId));
            model.addAttribute("chosen_pages", rolePageRepository.findAllByRoleId(chosenRoleId));
            model.addAttribute("change_role", roleRepository.getReferenceById(chosenRoleId));
        } else {
            model.addAttribute("chosen_pages", rolePageRepository.findAllByRoleId(initRoleId));
            model.addAttribute("change_role", new Role());
        }

        List<List<String>> elems = new ArrayList<>();
        for (UserRole userRole : userRoles){
            List<String> array = new ArrayList<>();
            array.add(userRole.getLogin());
            array.add(userRole.getPassword());
            array.add(userRole.getFullName());
            array.add(userRole.getRole().getInfo());
            elems.add(array);
        }
        System.out.println(userRoles);

        List<String> heads = new ArrayList<>();
        heads.add("Логин");
        heads.add("Пароль");
        heads.add("ФИО");
        heads.add("Роль");
        heads.add("Действия");

        model.addAttribute("roles_values", elems);
        model.addAttribute("roles_header", heads);

        return "admin";
    }

    @PostMapping("/create_user")
    public String create_user(@ModelAttribute UserRole userRole) {
        chosenRoleId = null;
        userRoleRepository.save(userRole);
        System.out.println(userRole);
        return "redirect:/role";
    }

    @PostMapping("/change")
    public String change_role(@ModelAttribute Role role, @RequestParam("pages_change") List<String> values) {
        System.out.println(role);
        roleRepository.save(role);
        List<RolePage> pages = rolePageRepository.findAllByRoleId(role.getId());
        for (RolePage page : pages) {
            page.setRead(false);
            page.setWrite(false);
        }
        setPageRolesToUser(role, values);
        System.out.println(rolePageRepository.findAllByRoleId(role.getId()));
        return "redirect:/role";
    }

    @PostMapping("/choose")
    public String choose_role(@ModelAttribute Role role) {
        chosenRoleId = role.getId();
        System.out.println(role);
        return "redirect:/role";
    }

    @PostMapping("/create")
    public String createRole(@ModelAttribute Role role, @RequestParam(value = "pages_create", required = false) List<String> values) {
        chosenRoleId = null;
        roleRepository.save(role);
        roleRepository.flush();
        System.out.println("Created role: " + role);
        createRolePages(role);
        setPageRolesToUser(role, values);
        System.out.println(rolePageRepository.findAllByRoleId(role.getId()));
        return "redirect:/role";
    }

    private void setPageRolesToUser(Role role, List<String> values) {
        System.out.println(values);
        if (values == null || values.isEmpty())
            return;
        for (String value : values) {
            String[] elems = value.split("_");
            RolePage rolePage = rolePageRepository.findByNameAndRoleId(elems[0], role.getId()).get();
            if (elems[1].equals("read")) {
                rolePage.setRead(true);
            } else {
                rolePage.setWrite(true);
            }
            rolePageRepository.save(rolePage);
        }
    }

    @GetMapping("/add_roles_manual")
    public String role_page() {
        Role role = saveRole();
        createRolePages(role);
        return "redirect:/role";
    }

    private void page(String name, String link, Role role) {
        rolePageRepository.save(new RolePage(name, link, role));
    }

    private void createRolePages(Role role) {
        page("Клиенты", "/clients", role);
        page("Договоры", "/contract", role);
        page("Контакты по договору", "/contact", role);
        page("Заявки", "/contact", role);
        page("Статусы заявок 'Заполнение'", "/contact", role);
        page("Статусы заявок 'Проверка'", "/contact", role);
        page("Статусы заявок 'Проверка ГБ'", "/contact", role);
        page("Статусы заявок 'В работе'", "/contact", role);
        page("Статусы заявок 'Выполнено'", "/contact", role);
        page("Инвойсы", "/contact", role);
        page("Курсы валют", "/contact", role);
        page("Календарь заявок", "/contact", role);
        page("Календарь проблемных клиентов", "/contact", role);
        page("Сервисы и налоги", "/contact", role);
        page("Администраторы и роли", "/role", role);
        page("Выполненные заявки", "/contact", role);
    }


}
