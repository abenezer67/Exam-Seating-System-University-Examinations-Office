package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Allocation;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Long> {
}
