package com.example.lestock.service;
import com.example.lestock.dao.UserDAO;
import com.example.lestock.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.save(user);
    }

    public Optional<User> findUserById(Long id) {
        return userDAO.findById(id);
    }

    public List<User> findAllUsers() {
        return userDAO.findAll();
    }

    public void  deleteUser(User user) {
        userDAO.delete(user);
    }

    public Optional<User> findByUserName(String userName) {
        return userDAO.findByUserName(userName);
    }

    public Optional<User> findByEmail(String email) {
        return userDAO.findByEmail(email);
    }
}
