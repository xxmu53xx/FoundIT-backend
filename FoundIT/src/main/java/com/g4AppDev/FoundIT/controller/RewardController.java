package com.g4AppDev.FoundIT.controller;

import com.g4AppDev.FoundIT.entity.Item;
import com.g4AppDev.FoundIT.entity.RewardEntity;
import com.g4AppDev.FoundIT.entity.UserEntity;
import com.g4AppDev.FoundIT.repository.RewardRepository;
import com.g4AppDev.FoundIT.service.RewardService;
import com.g4AppDev.FoundIT.service.UserService; // Add UserService if not already imported
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    @Autowired
    private RewardService rewardService;
    private RewardRepository rewardrepository;
    private RewardEntity rewardEntity;
    @Autowired
    private UserService userService; // Add UserService for user retrieval
    		
    @PostMapping("/postRewards")
    public ResponseEntity<RewardEntity> createReward(@RequestBody RewardEntity reward) {
        RewardEntity savedReward = rewardService.createReward(reward);
        return new ResponseEntity<>(savedReward, HttpStatus.CREATED);
    }
    //for updating isUsed to true, etc.

    @PutMapping("/putReward1/{rewardId}")
    public ResponseEntity<RewardEntity> updateReward1(
    		@PathVariable Long rewardId,
    	    @RequestBody RewardEntity updatedReward) {
    	    Optional<RewardEntity> existingReward = rewardrepository.findById(rewardId);
    	    if (existingReward.isPresent()) {
    	    	RewardEntity reward = existingReward.get();
    	        reward.setisUsed(updatedReward.getisUsed()); // Update the isUsed field

    	        // Update associated User if needed
    	        if (updatedReward.getUser() != null) {
    	            reward.setUser(updatedReward.getUser());
    	        }

    	        rewardrepository.save(reward);
    	        return ResponseEntity.ok(reward);
    	    } else {
    	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	    }
    }
    /*
    @PostMapping("/postRewards")
    public RewardEntity createReward(@RequestBody RewardEntity reward) {
    	return rewardService.createReward(reward);
    }*/
    @GetMapping("/getAllRewards")
    public ResponseEntity<List<RewardEntity>> getAllRewards() {
        List<RewardEntity> rewards = rewardService.getAllRewards();
        return new ResponseEntity<>(rewards, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RewardEntity> getRewardById(@PathVariable Long id) {
        Optional<RewardEntity> reward = rewardService.getRewardById(id);
        return reward.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/putReward/{rewardId}")
    public ResponseEntity<?> updateReward(
            @PathVariable Long rewardId,
            @RequestBody RewardEntity reward) {
        // Call the service with the correct object
        RewardEntity updatedReward = rewardService.updateReward(rewardId, reward);
        return ResponseEntity.ok(updatedReward);
    }
    
    
    
    /*
    public void deleteReward(Long id) {
        RewardEntity reward = rewardRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Reward not found"));
        reward.setUser(null); // Detach from user if needed
        rewardRepository.delete(reward);
    } */
    
    @DeleteMapping("/deleteRewards/{id}")
    public String deleteRewards(@PathVariable Long id) {
    	return rewardService.deleteReward(id);
    }

    
   
    @GetMapping("/getLatestRewards")
    public List<RewardEntity> getLatestRewards(@RequestParam(defaultValue = "5") int count) {
        return rewardService.getLatestReward(count);
    }
    @GetMapping("/getCountRewards")
    public ResponseEntity<Map<String, Long>> getEntityCounts() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("user_count", rewardService.getRewardCount());
        return ResponseEntity.ok(counts);
    }
    
    @GetMapping("/claimed-count")
    public long getClaimedRewardsCount() {
        return rewardService.getClaimedRewardsCount();
    }
}
