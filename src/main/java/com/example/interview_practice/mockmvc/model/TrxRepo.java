package com.example.interview_practice.mockmvc.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TrxRepo extends JpaRepository<Transaction, Long> {
}
