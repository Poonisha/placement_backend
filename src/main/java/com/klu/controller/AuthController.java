package com.klu.controller;

import com.klu.model.User;
import com.klu.repository.UserRepository;
import com.klu.model.Role;

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
            res.put("message", e.getMessage());
            return res;
        }
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {

        Map<String, Object> res = new HashMap<>();

        try {

            System.out.println("LOGIN EMAIL: " + user.getEmail());

            User existing = userRepository.findByEmail(user.getEmail()).orElse(null);

            if (existing == null) {
                res.put("success", false);
                res.put("message", "User not found");
                return res;
            }

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
            e.printStackTrace();

            res.put("success", false);
            res.put("message", e.getMessage());
            return res;
        }
    }

    // ✅ CREATE SINGLE TEST USER (student)
    @GetMapping("/create-test-user")
    public String createStudent() {

        if (userRepository.findByEmail("student@gmail.com").isPresent()) {
            return "Student already exists";
        }

        User user = new User();
        user.setName("Student");
        user.setEmail("student@gmail.com");
        user.setPassword("1234");
        user.setRole(Role.STUDENT);

        userRepository.save(user);

        return "Student created";
    }

    // 🔥 ✅ CREATE ALL USERS (FINAL FIX)
    @GetMapping("/setup-users")
    public String setupUsers() {

        if (userRepository.findByEmail("student@gmail.com").isEmpty()) {
            User u = new User();
            u.setName("Student");
            u.setEmail("student@gmail.com");
            u.setPassword("1234");
            u.setRole(Role.STUDENT);
            userRepository.save(u);
        }

        if (userRepository.findByEmail("employer@gmail.com").isEmpty()) {
            User u = new User();
            u.setName("Employer");
            u.setEmail("employer@gmail.com");
            u.setPassword("1234");
            u.setRole(Role.EMPLOYER);
            userRepository.save(u);
        }

        if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
            User u = new User();
            u.setName("Admin");
            u.setEmail("admin@gmail.com");
            u.setPassword("1234");
            u.setRole(Role.ADMIN);
            userRepository.save(u);
        }

        if (userRepository.findByEmail("po@gmail.com").isEmpty()) {
            User u = new User();
            u.setName("Placement Officer");
            u.setEmail("po@gmail.com");
            u.setPassword("1234");
            u.setRole(Role.PLACEMENT_OFFICER);
            userRepository.save(u);
        }

        return "All users created successfully";
    }
}