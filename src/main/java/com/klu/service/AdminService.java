package com.klu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.repository.UserRepository;
import com.klu.repository.JobRepository;
import com.klu.repository.ApplicationRepository;

import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    public Map<String, Object> getStats() {

        long totalUsers = userRepository.count();
        long totalJobs = jobRepository.count();
        long totalApplications = applicationRepository.count();

        return Map.of(
            "totalUsers", totalUsers,
            "totalJobs", totalJobs,
            "totalApplications", totalApplications
        );
    }
}