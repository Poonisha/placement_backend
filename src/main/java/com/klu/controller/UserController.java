package com.klu.controller;

import com.klu.model.User;
import com.klu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // ✅ UPDATE USER PROFILE
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {

        User existing = userRepository.findById(id).orElse(null);

        if (existing == null) {
            throw new RuntimeException("User not found");
        }

        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        existing.setPassword(user.getPassword());

        return userRepository.save(existing);
    }
}