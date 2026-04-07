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

    // ✅ APPLY JOB (UPDATED)
    @PostMapping("/apply")
    public Application apply(
            @RequestParam Long studentId,
            @RequestParam Long jobId) {

        return service.applyJob(studentId, jobId);
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

    // ✅ UPDATE STATUS (ENUM FIX)
    @PutMapping("/{id}/status")
    public Application updateStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status) {

        return service.updateStatus(id, status);
    }
}