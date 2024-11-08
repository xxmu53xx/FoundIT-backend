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
    
    
    public RewardEntity createReward(RewardEntity reward) {
        return rewardRepository.save(reward);
    }
    
    public List<RewardEntity> getAllRewards() {
        return rewardRepository.findAll();
    }
    
    public Optional<RewardEntity> getRewardById(Long id) {
        return rewardRepository.findById(id);
    }
    
    public RewardEntity updateReward(RewardEntity reward) {
        return rewardRepository.save(reward);
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
}