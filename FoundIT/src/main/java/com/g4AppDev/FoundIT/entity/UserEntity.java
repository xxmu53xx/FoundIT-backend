package com.g4AppDev.FoundIT.entity;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "user-entity")
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userID; // Primary Key ni
    
    private String schoolEmail;
    private String schoolId;
    private String password;
    private String bio;
    private int current_points;
    private boolean isAdmin;
    private boolean isLogged;
    //priority (mogana nani ay nani hilabti)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
    
    private List<Point> points;
    
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Item> items;
    
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
  @JsonManagedReference
    private List<RewardEntity> rewards;

    public UserEntity(Long userID) {
        this.userID = userID;
    }
    
    public boolean getIsAdmin() {
    	return this.isAdmin;   }
    
    public void setIsAdmin(boolean isAdmin) {
    	this.isAdmin=isAdmin;
    }
    
    public UserEntity() {}
    public Long getUserID() {
        return userID;
    }
    
    public boolean getIsLogged() {
    	return this.isLogged;    }
    
    public void setIsLogged(boolean isLogged) {
    	this.isLogged=isLogged;
    }
    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getSchoolEmail() {
        return schoolEmail;
    }

    public void setSchoolEmail(String school_email) {
        this.schoolEmail = school_email;
    }
    
    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String school_id) {
        this.schoolId = school_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getCurrentPoints() {
        return current_points;
    }

    public void setCurrentPoints(int current_points) {
        this.current_points = current_points;
    }
 
    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
   
    
    public List<Item> getItems() {
        return items;
    }
  
    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<RewardEntity> getRewards() {
        return rewards;
    }

    public void setRewards(List<RewardEntity> rewards) {
        this.rewards = rewards;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userID=" + userID +
                ", school_email='" + schoolEmail + '\'' +
                ", school_id='" + schoolId + '\'' +
                ", password='" + password + '\'' +
                ", bio='" + bio + '\'' +
                ", current_points=" + current_points +
                '}';
    }
}
