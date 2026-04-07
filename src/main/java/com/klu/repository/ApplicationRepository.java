package com.klu.repository;

import com.klu.model.Application;
import com.klu.model.ApplicationStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByStudentId(Long studentId);

    boolean existsByStudentIdAndJobId(Long studentId, Long jobId);

    long countByStudentId(Long studentId);   // 🔥 FIX

    long countByStudentIdAndStatus(Long studentId, ApplicationStatus status);
    List<Application> findByJobEmployerId(Long employerId);

    long countByJobEmployerId(Long employerId);

    long countByJobEmployerIdAndStatus(Long employerId, ApplicationStatus status);

    List<Application> findByJobId(Long jobId);
}