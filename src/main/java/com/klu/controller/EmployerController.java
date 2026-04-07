package com.klu.controller;

import com.klu.model.Application;
import com.klu.model.Job;
import com.klu.service.EmployerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/employer")
@CrossOrigin("*")
public class EmployerController {

    @Autowired
    private EmployerService service;

    // ✅ DASHBOARD STATS
    @GetMapping("/stats/{employerId}")
    public Map<String, Object> getStats(@PathVariable Long employerId) {

        Map<String, Object> map = new HashMap<>();

        map.put("totalJobs", service.getTotalJobs(employerId));
        map.put("totalApplications", service.getTotalApplications(employerId));
        map.put("pending", service.getPendingApplications(employerId));

        return map;
    }

    // ✅ JOB LISTINGS
    @GetMapping("/jobs/{employerId}")
    public List<Job> getJobs(@PathVariable Long employerId) {
        return service.getEmployerJobs(employerId);
    }

    // ✅ APPLICATIONS TABLE
    @GetMapping("/applications/{employerId}")
    public List<Application> getApplications(@PathVariable Long employerId) {
        return service.getEmployerApplications(employerId);
    }

    // ✅ CREATE JOB (FIXED)
    @PostMapping("/jobs/{employerId}")
    public Job createJob(@RequestBody Job job, @PathVariable Long employerId) {
        return service.createJob(job, employerId);
    }
}