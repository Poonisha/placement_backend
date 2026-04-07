package com.klu.controller;

import com.klu.service.ApplicationService;
import com.klu.repository.JobRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
@CrossOrigin("*")
public class StudentController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationService applicationService;

    // ✅ STUDENT DASHBOARD STATS
    @GetMapping("/stats/{studentId}")
    public Map<String, Object> getStats(@PathVariable Long studentId) {

        long totalJobs = jobRepository.count();

        long applied = applicationService.getTotalApplications(studentId);
        long shortlisted = applicationService.getShortlistedCount(studentId);
        long rejected = applicationService.getRejectedCount(studentId);

        Map<String, Object> map = new HashMap<>();
        map.put("totalJobs", totalJobs);
        map.put("applied", applied);
        map.put("shortlisted", shortlisted);
        map.put("rejected", rejected);

        return map;
    }
}