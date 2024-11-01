package com.g4AppDev.FoundIT.service;

import com.g4AppDev.FoundIT.entity.RewardEntity;
import com.g4AppDev.FoundIT.entity.UserEntity;
import com.g4AppDev.FoundIT.repository.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RewardService {
    
    @Autowired
    private RewardRepository rewardRepository;
    
    // Create
    public RewardEntity createReward(RewardEntity reward) {
        return rewardRepository.save(reward);
    }
    
    // Read all
    public List<RewardEntity> getAllRewards() {
        return rewardRepository.findAll();
    }
    
    // Read one
    public Optional<RewardEntity> getRewardById(Long id) {
        return rewardRepository.findById(id);
    }
    
    // Update
    public RewardEntity updateReward(RewardEntity reward) {
        return rewardRepository.save(reward);
    }
    
    // Delete
    public void deleteReward(Long id) {
        rewardRepository.deleteById(id);
    }
    
    public List<RewardEntity> getLatestReward(int count) {
        return rewardRepository.findAll().stream()
            .sorted((u1, u2) -> Long.compare(u2.getRewardId(), u1.getRewardId())) // Sort by User ID descending
            .limit(count) // Limit to the specified count
            .collect(Collectors.toList());
    }
}