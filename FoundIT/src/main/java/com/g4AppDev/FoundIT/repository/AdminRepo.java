package com.g4AppDev.FoundIT.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.g4AppDev.FoundIT.entity.Admin;

public interface AdminRepo extends JpaRepository<Admin, Long> {
  
}