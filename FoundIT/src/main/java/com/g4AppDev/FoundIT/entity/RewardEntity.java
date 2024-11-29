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
    
    //for display purposes
    //record keeping for admin and disable display for users if true
    private boolean isClaimed;
    
    //for reward
    private String couponCode;
    
    //for reward picture
    @Lob
    private String image;
    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id", nullable = true)
    @JsonBackReference
    private UserEntity user;	
    
    public void setUser(UserEntity user) {
    	this.user=user;
    }
    public String getcouponCode() {
    	return this.couponCode;
    }
    public void setImage(String image) {
    	this.image=image;
    }
    public String getImage() {
    	return this.image;
    }
    public void setcouponCode(String couponCode) {
    	this.couponCode=couponCode;
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
    
    public void removeUser() {
        if (this.user != null) {
            this.user = null;
        }
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }
}