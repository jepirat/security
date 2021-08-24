package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import web.model.Role;
import web.model.User;
import web.services.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {
    @Autowired
    UserService userService;
    @GetMapping("/")
    public String hello() {
//        Set<Role> roleSet = new HashSet<>();
//        roleSet.add(Role.ADMIN);
//        User user = new User("admin", "vasya", "pupkin", "12345",roleSet);
//        userService.add(user);
        return "/hello";
    }
}
