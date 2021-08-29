package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.services.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")

public class AdminController {
    @Autowired
    UserService userService;

    @GetMapping
    public String usersController(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("userList", users);
        return "admin/index";
    }

    @GetMapping("/new")
    public String show(Model model) {
        model.addAttribute("user", new User());
        return "admin/new";
    }

    @PostMapping
    public String add(@ModelAttribute("user") User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        user.setRoles(roles);
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("list", Role.values());
        model.addAttribute("user", userService.getUserById(id));
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id, @RequestParam(name = "role", required = false) String role) {
        Set<Role> roles = userService.getUserById(id).getRole();
        if (role != null && role.equals("ADMIN")) {
            roles.add(Role.valueOf(role));
        } else if (role != null && role.equals("USER")) {
            roles.remove(Role.ADMIN);
        }
        user.setRoles(roles);
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}