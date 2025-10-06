package com.example.lestock.service;
import com.example.lestock.dao.UserDAO;
import com.example.lestock.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public Optional<User> findByUserName(String userName) {
        return userDAO.findByUserName(userName);
    }

    public Optional<User> findUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }
}
