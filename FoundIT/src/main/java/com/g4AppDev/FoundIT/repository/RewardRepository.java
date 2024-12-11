package com.g4AppDev.FoundIT.repository;

import com.g4AppDev.FoundIT.entity.RewardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardRepository extends JpaRepository<RewardEntity, Long> {
	long deleteById(long id);
	
	@Query("SELECT COUNT(r) FROM RewardEntity r WHERE r.isClaimed = false")
    long countClaimedRewards();
	
}