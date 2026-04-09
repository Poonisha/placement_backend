package com.klu.controller;

import com.klu.model.Job;
import com.klu.model.User;
import com.klu.repository.JobRepository;
import com.klu.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin("*")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ ADD JOB
    @PostMapping("/add/{employerId}")
    public ResponseEntity<Job> addJob(@PathVariable Long employerId, @RequestBody Job job) {

        User employer = userRepository.findById(employerId)
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        job.setEmployer(employer);

        Job savedJob = jobRepository.save(job);
        return ResponseEntity.ok(savedJob);
    }

    // ✅ GET ALL JOBS
    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobRepository.findAll());
    }

    // ✅ GET JOBS BY EMPLOYER
    @GetMapping("/employer/{employerId}")
    public ResponseEntity<List<Job>> getJobsByEmployer(@PathVariable Long employerId) {
        return ResponseEntity.ok(jobRepository.findByEmployerId(employerId));
    }

    // ✅ UPDATE JOB
    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job updatedJob) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        job.setTitle(updatedJob.getTitle());
        job.setCompanyName(updatedJob.getCompanyName());
        job.setDescription(updatedJob.getDescription());

        Job saved = jobRepository.save(job);
        return ResponseEntity.ok(saved);
    }

    // ✅ DELETE JOB
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {

        if (!jobRepository.existsById(id)) {
            throw new RuntimeException("Job not found");
        }

        jobRepository.deleteById(id);
        return ResponseEntity.ok("Job deleted successfully");
    }
}