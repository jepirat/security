package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.services.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")

public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public String usersController(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("userList", users);
        return "users/index";
    }

    @GetMapping("/new")
    public String shiw(Model model) {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        User user = new User();
        user.setRoles(roles);
        model.addAttribute("user", user);
        return "users/new";
    }

    @PostMapping
    public String add(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        Set<String> list = new HashSet<>();
       for (Role r : Role.values()) {
           list.add(r.name());
        }
        model.addAttribute("list", list);
        model.addAttribute("user", userService.getUserById(id));
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id, @RequestParam String role) {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.valueOf(role));
        user.setRoles(roles);
        userService.updateUser(id, user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

//    @PostMapping("/login")
//    public String login() {
//        return "users/login";
//    }
}