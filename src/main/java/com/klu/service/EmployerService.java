package com.klu.service;

import com.klu.model.Application;
import com.klu.model.ApplicationStatus;
import com.klu.model.Job;
import com.klu.model.User;
import com.klu.repository.ApplicationRepository;
import com.klu.repository.JobRepository;
import com.klu.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployerService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    // ✅ CREATE JOB
    public Job createJob(Job job, Long employerId) {

        User employer = userRepository.findById(employerId)
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        job.setEmployer(employer);

        return jobRepository.save(job);
    }

    // ✅ GET ALL JOBS OF EMPLOYER
    public List<Job> getEmployerJobs(Long employerId) {
        return jobRepository.findByEmployerId(employerId);
    }

    // ✅ GET ALL APPLICATIONS FOR EMPLOYER
    public List<Application> getEmployerApplications(Long employerId) {
        return applicationRepository.findByJobEmployerId(employerId);
    }

    // ✅ TOTAL JOBS COUNT
    public long getTotalJobs(Long employerId) {
        return jobRepository.countByEmployerId(employerId);
    }

    // ✅ TOTAL APPLICATIONS COUNT
    public long getTotalApplications(Long employerId) {
        return applicationRepository.countByJobEmployerId(employerId);
    }

    // ✅ PENDING APPLICATIONS (APPLIED)
    public long getPendingApplications(Long employerId) {
        return applicationRepository.countByJobEmployerIdAndStatus(
                employerId, ApplicationStatus.APPLIED);
    }

    // ✅ GET APPLICANTS FOR A JOB (OPTIONAL EXTRA)
    public List<Application> getApplicants(Long jobId) {
        return applicationRepository.findByJobId(jobId);
    }
}