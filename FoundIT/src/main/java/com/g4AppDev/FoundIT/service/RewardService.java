package com.g4AppDev.FoundIT.service;

import com.g4AppDev.FoundIT.entity.RewardEntity;
import com.g4AppDev.FoundIT.entity.UserEntity;
import com.g4AppDev.FoundIT.repository.RewardRepository;
import com.g4AppDev.FoundIT.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RewardService {
    
    @Autowired
    private RewardRepository rewardRepository;
    
    private UserRepo userRepository; 
    
    @Autowired
    public RewardService(RewardRepository rewardRepository, UserRepo userRepository) {
        this.rewardRepository = rewardRepository;
        this.userRepository = userRepository;
    }
    public RewardEntity createReward(RewardEntity reward) {
        return rewardRepository.save(reward);
    }
    
    public List<RewardEntity> getAllRewards() {
        return rewardRepository.findAll();
    }
    
    public Optional<RewardEntity> getRewardById(Long id) {
        return rewardRepository.findById(id);
    }
    
    public RewardEntity updateReward(Long rewardId, RewardEntity updatedReward) {
    	RewardEntity existingReward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new IllegalArgumentException("Reward not found with id: " + rewardId));

        // Fetch the existing user
        UserEntity existingUser = userRepository.findById(updatedReward.getUser().getUserID())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + updatedReward.getUser().getUserID()));

        // Update reward fields
        existingReward.setPointsRequired(updatedReward.getPointsRequired());
        existingReward.setRewardName(updatedReward.getRewardName());
        existingReward.setRewardType(updatedReward.getRewardType());
        existingReward.setUser(existingUser);
        existingReward.setisClaimed(updatedReward.getisClaimed());
        return rewardRepository.save(existingReward);
    }
    
    public void deleteReward(Long id) {
        rewardRepository.deleteById(id);
    }
    
    public List<RewardEntity> getLatestReward(int count) {
        return rewardRepository.findAll().stream()
            .sorted((u1, u2) -> Long.compare(u2.getRewardId(), u1.getRewardId()))
            .limit(count)
            .collect(Collectors.toList());
    }
    
    public long getRewardCount() {
        return rewardRepository.count();
    }
}