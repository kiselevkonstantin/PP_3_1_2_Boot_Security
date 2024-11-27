package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/allUsers";
    }


    @GetMapping("/showUserByID")
    public String getUserByID(@RequestParam("id") long id, Model model) { // (value = "id", required = false, defaultValue = "0")
        model.addAttribute("user", userService.getById(id));
        return "/showUser";
    }


    @GetMapping("/new")
    public String addNewUser(@ModelAttribute("user") User user) {
        return "/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("user") User newUser) {
        userService.saveUser(newUser);
        return "redirect:/admin";
    }


    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") int id) {
        userService.delete((long) id);
        return "redirect:/admin";
    }


    @GetMapping("/update")
    public String editUser(@RequestParam("id") long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "/update";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("id") int id) {
        userService.update(id, user);
        return "redirect:/admin";
    }

    @GetMapping("/showMyProfile")
    public String showMyPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUsername(username);

        model.addAttribute("user", user);
        return "/showUser";
    }
}
