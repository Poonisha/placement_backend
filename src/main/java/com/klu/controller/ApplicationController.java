package com.klu.controller;

import com.klu.model.Application;
import com.klu.model.ApplicationStatus;
import com.klu.service.ApplicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin("*")
public class ApplicationController {

    @Autowired
    private ApplicationService service;

    // ✅ APPLY JOB (FIXED - USE RequestBody)
    @PostMapping("/apply")
    public Application apply(@RequestBody Application app) {
        return service.applyJob(app);
    }

    // ✅ GET ALL APPLICATIONS
    @GetMapping
    public List<Application> getAll() {
        return service.getAllApplications();
    }

    // ✅ GET APPLICATIONS BY STUDENT
    @GetMapping("/student/{id}")
    public List<Application> getByStudent(@PathVariable Long id) {
        return service.getApplicationsByStudent(id);
    }

    // ✅ UPDATE STATUS
    @PutMapping("/{id}/status")
    public Application updateStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status) {

        return service.updateStatus(id, status);
    }
}