package com.jinu.executor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jinu.executor.model.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

}
