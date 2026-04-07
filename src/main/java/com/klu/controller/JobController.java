package com.klu.controller;

import com.klu.model.Job;
import com.klu.model.User;
import com.klu.repository.JobRepository;
import com.klu.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "http://localhost:5174")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Add Job
    @PostMapping("/add/{employerId}")
    public Job addJob(@PathVariable Long employerId, @RequestBody Job job) {

        User employer = userRepository.findById(employerId)
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        job.setEmployer(employer);

        return jobRepository.save(job);
    }

    // ✅ Get all jobs
    @GetMapping
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // ✅ Get jobs by employer
    @GetMapping("/employer/{employerId}")
    public List<Job> getJobsByEmployer(@PathVariable Long employerId) {
        return jobRepository.findByEmployerId(employerId);
    }

    // ✅ Delete job
    @DeleteMapping("/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobRepository.deleteById(id);
        return "Job deleted";
    }

    // ✅ Update job
    @PutMapping("/{id}")
    public Job updateJob(@PathVariable Long id, @RequestBody Job updatedJob) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        job.setTitle(updatedJob.getTitle());
        job.setCompanyName(updatedJob.getCompanyName());
        job.setDescription(updatedJob.getDescription());

        return jobRepository.save(job);
    }
}