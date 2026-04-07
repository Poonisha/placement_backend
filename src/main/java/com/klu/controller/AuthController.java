package com.klu.controller;

import com.klu.model.User;
import com.klu.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // ✅ REGISTER
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {

        Map<String, Object> res = new HashMap<>();

        try {

            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                res.put("success", false);
                res.put("message", "Password is required");
                return res;
            }

            User existing = userRepository.findByEmail(user.getEmail()).orElse(null);

            if (existing != null) {
                res.put("success", false);
                res.put("message", "Email already registered");
                return res;
            }

            User saved = userRepository.save(user);

            res.put("success", true);
            res.put("user", saved);
            res.put("message", "Registered successfully");

            return res;

        } catch (Exception e) {
            e.printStackTrace();

            res.put("success", false);
            res.put("message", "Server error");
            return res;
        }
    }

    // ✅ LOGIN (FINAL FIXED - NO ERRORS)
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {

        Map<String, Object> res = new HashMap<>();

        try {

            User existing = userRepository.findByEmail(user.getEmail()).orElse(null);

            if (existing == null) {
                res.put("success", false);
                res.put("message", "User not found");
                return res;
            }

            // ✅ SAFE PASSWORD CHECK (NO crash)
            if (existing.getPassword() == null ||
                user.getPassword() == null ||
                !existing.getPassword().equals(user.getPassword().trim())) {

                res.put("success", false);
                res.put("message", "Invalid password");
                return res;
            }

            res.put("success", true);
            res.put("user", existing);
            res.put("token", "dummy-token-" + existing.getId());

            return res;

        } catch (Exception e) {
            e.printStackTrace(); // see error in console

            res.put("success", false);
            res.put("message", "Server error");
            return res;
        }
    }
}