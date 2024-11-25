package com.g4AppDev.FoundIT.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "reward")
public class RewardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rewardId;
    
    private Integer pointsRequired;
    private String rewardName;
    private String rewardType;
    private boolean isClaimed;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserEntity user;	
    
    public void setUser(UserEntity user) {
    	this.user=user;
    }
    public void setisClaimed(boolean isClaimed) {
    	this.isClaimed=isClaimed;
    }
    
    public boolean getisClaimed() {
    	return this.isClaimed;
    }
    
    public UserEntity getUser() {
    	return this.user;
    }		
    public Long getRewardId() {
        return rewardId;
    }

    public void setRewardId(Long rewardId) {
        this.rewardId = rewardId;
    }

    public Integer getPointsRequired() {
        return pointsRequired;
    }

    public void setPointsRequired(Integer pointsRequired) {
        this.pointsRequired = pointsRequired;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }
}