package com.g4AppDev.FoundIT.controller;

import com.g4AppDev.FoundIT.entity.RewardEntity;
import com.g4AppDev.FoundIT.entity.UserEntity;
import com.g4AppDev.FoundIT.service.RewardService;
import com.g4AppDev.FoundIT.service.UserService; // Add UserService if not already imported
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    @Autowired
    private UserService userService; // Add UserService for user retrieval
    
    @PostMapping("/postRewards")
    public ResponseEntity<RewardEntity> createReward(@RequestBody RewardEntity reward) {
        RewardEntity savedReward = rewardService.createReward(reward);
        return new ResponseEntity<>(savedReward, HttpStatus.CREATED);
    }

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

    @PutMapping("/putReward/{id}")
    public ResponseEntity<RewardEntity> updateReward(@PathVariable Long id, @RequestBody RewardEntity reward) {
        Optional<RewardEntity> existingReward = rewardService.getRewardById(id);
        if (!existingReward.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Ensure user is set in the RewardEntity
        Long userId = reward.getUser().getUserID(); // Assuming the user is provided in the request
        UserEntity user = userService.getUserById(userId); // Fetch user from database
        
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // If user not found, return bad request
        }

        reward.setUser(user); // Set the user in reward

        reward.setRewardId(id); // Set the ID of the reward
        RewardEntity updatedReward = rewardService.updateReward(reward); // Save the updated reward
        return new ResponseEntity<>(updatedReward, HttpStatus.OK);
    }

    @DeleteMapping("/deleteRewards/{id}")
    public ResponseEntity<Void> deleteReward(@PathVariable Long id) {
        if (!rewardService.getRewardById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        rewardService.deleteReward(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getLatestRewards")
    public List<RewardEntity> getLatestRewards(@RequestParam(defaultValue = "5") int count) {
        return rewardService.getLatestReward(count);
    }
}
