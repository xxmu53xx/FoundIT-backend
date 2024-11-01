package com.g4AppDev.FoundIT.repository;

import com.g4AppDev.FoundIT.entity.RewardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardRepository extends JpaRepository<RewardEntity, Long> {
}