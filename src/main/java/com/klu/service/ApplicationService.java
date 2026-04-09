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
public class ApplicationService {

    @Autowired
    private ApplicationRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    // ✅ APPLY JOB
    public Application applyJob(Long studentId, Long jobId) {

        // ❌ Prevent duplicate
        if (repository.existsByStudentIdAndJobId(studentId, jobId)) {
            throw new RuntimeException("Already applied to this job");
        }

        // ✅ Fetch student
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // ✅ Fetch job
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // ✅ Create application
        Application app = new Application();
        app.setStudent(student);
        app.setJob(job);
        app.setStatus(ApplicationStatus.APPLIED);

        return repository.save(app);
    }

    // ✅ UPDATE STATUS
    public Application updateStatus(Long id, ApplicationStatus status) {

        Application app = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus(status);

        return repository.save(app);
    }

    // ✅ GET ALL APPLICATIONS
    public List<Application> getAllApplications() {
        return repository.findAll();
    }

    // ✅ GET APPLICATIONS BY STUDENT
    public List<Application> getApplicationsByStudent(Long studentId) {
        return repository.findByStudentId(studentId);
    }

    // ✅ TOTAL APPLICATIONS
    public long getTotalApplications(Long studentId) {
        return repository.countByStudentId(studentId);
    }

    // ✅ COUNT APPLIED
    public long getAppliedCount(Long studentId) {
        return repository.countByStudentIdAndStatus(studentId, ApplicationStatus.APPLIED);
    }

    // ✅ COUNT SHORTLISTED
    public long getShortlistedCount(Long studentId) {
        return repository.countByStudentIdAndStatus(studentId, ApplicationStatus.SHORTLISTED);
    }

    // ✅ COUNT REJECTED
    public long getRejectedCount(Long studentId) {
        return repository.countByStudentIdAndStatus(studentId, ApplicationStatus.REJECTED);
    }
    public Application applyJob(Application app) {
        app.setStatus(ApplicationStatus.APPLIED);
        return repository.save(app);
    }
}