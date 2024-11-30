package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/allUsers";
    }


    @GetMapping("/showUserByID")
    public String getUserByID(@RequestParam("id") long id, Model model) { // (value = "id", required = false, defaultValue = "0")
        model.addAttribute("user", userService.getById(id));
        return "showAdminUser";
    }


    @GetMapping("/new")
    public String addNewUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getDemandedRoles());
        return "new";
    }

    @PostMapping("/addUser")
    public String create(@ModelAttribute("user") User newUser) {
        userService.saveUser(newUser);
        return "redirect:/admin";
    }


    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }


    @GetMapping("/update")
    public String editUser(@RequestParam("id") long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("roles", roleService.getDemandedRoles());
        return "/update";
    }

    @PatchMapping("/save")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("id") int id,
                             @RequestParam(value = "role") String role) {
        userService.update(id, user, role);
        return "redirect:/admin";
    }

    @GetMapping("/showMyProfile")
    public String showMyPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUsername(username);

        model.addAttribute("user", user);
        return "showAdminUser";
    }
}
