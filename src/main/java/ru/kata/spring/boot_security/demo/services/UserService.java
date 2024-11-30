package ru.kata.spring.boot_security.demo.services;

import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User getById(Long id);

    void saveUser(User user);

    void update(long id, User updatedUser, String role);

    void delete(Long id);

    @Transactional(readOnly = true)
    User findByUsername(String username);

    void setEncryptedPassword(User user);


}
