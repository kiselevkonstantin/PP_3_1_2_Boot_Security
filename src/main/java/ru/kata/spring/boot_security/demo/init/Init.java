package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class Init implements CommandLineRunner {

    private RoleService roleService;
    private UserService userService;

    @Autowired
    public Init(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {

        Collection<Role> adminRoles = new ArrayList<>();
        Collection<Role> userRoles = new ArrayList<>();

        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");

        roleService.save(adminRole);
        roleService.save(userRole);

        adminRoles.add(adminRole);
        userRoles.add(userRole);

        User firstUser = new User("Konstantin", "123", "kiselev@com", adminRoles);
        User secondUser = new User("Alex", "345", "alex@com", userRoles);

        userService.saveUser(firstUser);
        userService.saveUser(secondUser);


    }
}
