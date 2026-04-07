package com.klu.controller;

import com.klu.model.Application;
import com.klu.model.Job;
import com.klu.model.User;
import com.klu.repository.ApplicationRepository;
import com.klu.repository.JobRepository;
import com.klu.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    // ✅ 1. GET ALL USERS
    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // ✅ 2. DELETE USER
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "User deleted successfully";
    }

    // ✅ 3. GET ALL JOBS
    @GetMapping("/jobs")
    public List<Job> getJobs() {
        return jobRepository.findAll();
    }

    // ✅ 4. DELETE JOB
    @DeleteMapping("/jobs/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobRepository.deleteById(id);
        return "Job deleted successfully";
    }

    // ✅ 5. ADMIN STATS (DASHBOARD)
    @GetMapping("/stats")
    public Map<String, Object> getStats() {

        Map<String, Object> map = new HashMap<>();

        map.put("totalUsers", userRepository.count());
        map.put("totalJobs", jobRepository.count());
        map.put("totalApplications", applicationRepository.count());

        return map;
    }

    // ✅ 6. PLACEMENTS (YOU ALREADY USED)
    @GetMapping("/placements")
    public List<Application> getPlacements() {
        return applicationRepository.findAll();
    }
    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}