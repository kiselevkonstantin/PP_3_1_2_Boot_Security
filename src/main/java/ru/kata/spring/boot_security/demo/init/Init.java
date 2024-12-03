package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class Init implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private RoleService roleService;
    private UserService userService;

    @Autowired
    public Init(RoleService roleService, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        List<Role> firstUserRole = new ArrayList<>();
        List<Role> secondUserRole = new ArrayList<>();

        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");

        firstUserRole.add(adminRole);
        secondUserRole.add(userRole);

        roleService.save(adminRole);
        roleService.save(userRole);

        User firstUser = new User("Konstantin", passwordEncoder.encode("123"), "kiselev@com", firstUserRole);
        User secondUser = new User("Alex", passwordEncoder.encode("345"), "alex@com", secondUserRole);

        userService.saveUser(firstUser);
        userService.saveUser(secondUser);


    }
}
